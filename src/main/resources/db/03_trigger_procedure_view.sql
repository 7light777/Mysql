-- ============================================================
-- 篮球数据管理系统 - 数据库对象脚本
-- 包含：触发器、事务存储过程、更新存储过程、视图
-- 对应课程评分点：事务删除 / 触发器添加 / 存储过程更新 / 视图查询
-- ============================================================

USE basketball_db;

-- 先删除旧对象，避免重复创建报错
DROP TRIGGER IF EXISTS trg_scoutnote_before_insert;
DROP PROCEDURE IF EXISTS sp_delete_game_with_stats_transaction;
DROP PROCEDURE IF EXISTS sp_update_player_game_stats;
DROP VIEW IF EXISTS v_player_game_detail;

-- 修改语句分隔符，因为存储过程体内有分号
DELIMITER //

-- ============================================================
-- 四、触发器：球探报告添加前的数据校验
-- 触发时机：BEFORE INSERT ON ScoutNote
-- 作用：拦截非法数据（空值、越界、空字符串）
-- ============================================================
CREATE TRIGGER trg_scoutnote_before_insert
BEFORE INSERT ON ScoutNote           -- 在数据插入 ScoutNote 表【之前】触发
FOR EACH ROW                         -- 行级触发器，每插入一行执行一次
BEGIN
    -- 校验1：潜力评分不能为空
    IF NEW.potential_rating IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'potential_rating不能为空';
    END IF;

    -- 校验2：潜力评分必须在 1~10 范围内
    IF NEW.potential_rating < 1 OR NEW.potential_rating > 10 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'potential_rating必须在1到10之间';
    END IF;

    -- 校验3：球探报告内容不能为空（含纯空格）
    IF NEW.content IS NULL OR TRIM(NEW.content) = '' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'content不能为空';
    END IF;
END//


-- ============================================================
-- 三、事务存储过程：删除比赛及关联的球员表现记录
-- 调用方式：CALL sp_delete_game_with_stats_transaction(比赛ID)
-- 特点：显式事务控制（START TRANSACTION / COMMIT / ROLLBACK）
-- ============================================================
CREATE PROCEDURE sp_delete_game_with_stats_transaction(
    IN p_game_id INT                  -- 输入：要删除的比赛ID
)
BEGIN
    -- ----- 变量声明 -----
    DECLARE v_game_count          INT DEFAULT 0;   -- 比赛是否存在（0=不存在，1=存在）
    DECLARE v_total_score         INT DEFAULT 0;   -- 两队总分（主队得分 + 客队得分）
    DECLARE v_deleted_stats_count INT DEFAULT 0;   -- 删除的球员表现记录条数
    DECLARE v_score_threshold     INT DEFAULT 230; -- 重点比赛分数线：总分>=230不允许删除

    -- ----- 异常处理器：捕获任何SQL异常，自动回滚 -----
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;   -- 撤销事务中所有已执行的操作
        RESIGNAL;   -- 把原始错误继续向上抛出给 Java 端
    END;

    -- ----- 开启事务 -----
    START TRANSACTION;

    -- ----- 查询比赛是否存在 + 计算两队总分 -----
    SELECT COUNT(*),
           COALESCE(MAX(home_score), 0) + COALESCE(MAX(away_score), 0)
    INTO v_game_count,               -- → 存入比赛存在标志
         v_total_score               -- → 存入两队总分
    FROM Game
    WHERE game_id = p_game_id;

    -- ----- 比赛不存在则抛异常，触发回滚 -----
    IF v_game_count = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '比赛不存在，无法删除';
    END IF;

    -- ----- 第1步：先删除子表数据（外键约束要求先删子表）-----
    DELETE FROM PlayerGameStats
    WHERE game_id = p_game_id;

    -- ROW_COUNT() 返回上一条 DELETE 影响的行数
    SET v_deleted_stats_count = ROW_COUNT();

    -- ----- 第2步：检查是否为重点比赛-----
    IF v_total_score >= v_score_threshold THEN
        -- 主动抛异常 → HANDLER 捕获 → ROLLBACK → 第1步的删除被撤销
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '该比赛属于重点比赛，不允许删除；事务已回滚';
    END IF;

    -- ----- 第3步：删除父表数据 -----
    DELETE FROM Game
    WHERE game_id = p_game_id;

    -- ----- 提交事务：以上所有删除操作正式生效 -----
    COMMIT;
END//


-- ============================================================
-- 五、存储过程：更新球员单场比赛技术统计，并同步比赛比分
-- 调用方式：CALL sp_update_player_game_stats(球员ID, 比赛ID, 得分, 篮板, 助攻, 抢断, 盖帽)
-- 核心逻辑：得分差值 = 新得分 - 旧得分，差值累加到对应球队比分
-- ============================================================
CREATE PROCEDURE sp_update_player_game_stats(
    IN p_player_id INT,               -- 输入：球员ID
    IN p_game_id INT,                 -- 输入：比赛ID
    IN p_points INT,                  -- 输入：新得分
    IN p_rebounds INT,                -- 输入：新篮板
    IN p_assists INT,                 -- 输入：新助攻
    IN p_steals INT,                  -- 输入：新抢断
    IN p_blocks INT                   -- 输入：新盖帽
)
BEGIN
    -- ----- 变量声明 -----
    DECLARE v_exists         INT DEFAULT 0;    -- 记录是否存在（0=不存在，1=存在）
    DECLARE v_old_points     INT DEFAULT 0;    -- 球员原来的得分（用于计算差值）
    DECLARE v_player_team_id INT DEFAULT NULL; -- 球员当前所属球队ID
    DECLARE v_home_team_id   INT DEFAULT NULL; -- 比赛主队ID
    DECLARE v_away_team_id   INT DEFAULT NULL; -- 比赛客队ID
    DECLARE v_score_delta    INT DEFAULT 0;    -- 得分差值 = 新得分 - 旧得分

    -- ----- 参数合法性校验：不能为NULL，不能为负数 -----
    IF p_points IS NULL OR p_rebounds IS NULL OR p_assists IS NULL
        OR p_steals IS NULL OR p_blocks IS NULL
        OR p_points < 0 OR p_rebounds < 0 OR p_assists < 0
        OR p_steals < 0 OR p_blocks < 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '技术统计数据不能为负数';
    END IF;

    -- ----- 检查 (球员ID, 比赛ID) 记录是否存在 -----
    SELECT COUNT(*)
    INTO v_exists
    FROM PlayerGameStats pgs
    JOIN Player p ON p.player_id = pgs.player_id       -- 关联球员表确认球员存在
    JOIN Game g ON g.game_id = pgs.game_id             -- 关联比赛表确认比赛存在
    WHERE pgs.player_id = p_player_id
      AND pgs.game_id = p_game_id;

    IF v_exists = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '未找到对应球员比赛技术统计记录';
    END IF;

    -- ----- 查询旧得分 + 球员球队归属 + 比赛主客队信息 -----
    SELECT pgs.points,               -- 原来的得分 → v_old_points
           ap.current_team_id,       -- 球员所属球队 → v_player_team_id
           g.home_team_id,           -- 主队ID → v_home_team_id
           g.away_team_id            -- 客队ID → v_away_team_id
    INTO v_old_points,
         v_player_team_id,
         v_home_team_id,
         v_away_team_id
    FROM PlayerGameStats pgs
    JOIN Player p ON p.player_id = pgs.player_id
    LEFT JOIN ActivePlayer ap ON ap.player_id = p.player_id  -- LEFT JOIN 兼容退役球员
    JOIN Game g ON g.game_id = pgs.game_id
    WHERE pgs.player_id = p_player_id
      AND pgs.game_id = p_game_id;

    -- ----- 计算得分差值 -----
    SET v_score_delta = p_points - v_old_points;

    -- ----- 根据球员所属球队，将差值同步到对应比分 -----
    IF v_player_team_id = v_home_team_id THEN
        -- 球员属于主队 → 调整主队比分
        UPDATE Game
        SET home_score = home_score + v_score_delta
        WHERE game_id = p_game_id;
    ELSEIF v_player_team_id = v_away_team_id THEN
        -- 球员属于客队 → 调整客队比分
        UPDATE Game
        SET away_score = away_score + v_score_delta
        WHERE game_id = p_game_id;
    ELSE
        -- 无法判断归属 → 抛异常阻止更新
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '无法判断该球员所属球队，不能同步更新比赛比分';
    END IF;

    -- ----- 更新球员技术统计 -----
    UPDATE PlayerGameStats
    SET points   = p_points,
        rebounds = p_rebounds,
        assists  = p_assists,
        steals   = p_steals,
        blocks   = p_blocks
    WHERE player_id = p_player_id
      AND game_id = p_game_id;
END//

-- 恢复默认分隔符
DELIMITER ;


-- ============================================================
-- 六、视图：球员比赛表现详情（封装4表联查）
-- 查询方式：SELECT * FROM v_player_game_detail WHERE ...
-- Team 表被连接两次（ht=主队, at=客队）
-- ============================================================
CREATE VIEW v_player_game_detail AS
SELECT
    p.player_id,                     -- 球员ID
    p.name        AS player_name,     -- 球员姓名
    g.game_id,                        -- 比赛ID
    g.game_date,                      -- 比赛日期
    ht.team_name AS home_team,        -- 主队名称（Team表第1次连接）
    at.team_name AS away_team,        -- 客队名称（Team表第2次连接）
    pgs.points,                       -- 得分
    pgs.rebounds,                     -- 篮板
    pgs.assists,                      -- 助攻
    pgs.steals,                       -- 抢断
    pgs.blocks                        -- 盖帽
FROM PlayerGameStats pgs
JOIN Player p  ON p.player_id   = pgs.player_id     -- 关联球员表，取球员姓名
JOIN Game   g  ON g.game_id     = pgs.game_id       -- 关联比赛表，取日期和主客队ID
JOIN Team   ht ON ht.team_id    = g.home_team_id    -- 关联Team表(别名ht)，取主队名称
JOIN Team   at ON at.team_id    = g.away_team_id;   -- 关联Team表(别名at)，取客队名称
