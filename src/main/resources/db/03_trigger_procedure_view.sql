USE basketball_db;

DROP TRIGGER IF EXISTS trg_scoutnote_before_insert;
DROP PROCEDURE IF EXISTS sp_update_player_game_stats;
DROP VIEW IF EXISTS v_player_game_detail;

DELIMITER //

CREATE TRIGGER trg_scoutnote_before_insert
BEFORE INSERT ON ScoutNote
FOR EACH ROW
BEGIN
    IF NEW.potential_rating IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'potential_rating不能为空';
    END IF;

    IF NEW.potential_rating < 1 OR NEW.potential_rating > 10 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'potential_rating必须在1到10之间';
    END IF;

    IF NEW.content IS NULL OR TRIM(NEW.content) = '' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'content不能为空';
    END IF;
END//

CREATE PROCEDURE sp_update_player_game_stats(
    IN p_player_id INT,
    IN p_game_id INT,
    IN p_points INT,
    IN p_rebounds INT,
    IN p_assists INT,
    IN p_steals INT,
    IN p_blocks INT
)
BEGIN
    DECLARE v_exists INT DEFAULT 0;
    DECLARE v_old_points INT DEFAULT 0;
    DECLARE v_player_team_id INT DEFAULT NULL;
    DECLARE v_home_team_id INT DEFAULT NULL;
    DECLARE v_away_team_id INT DEFAULT NULL;
    DECLARE v_score_delta INT DEFAULT 0;

    IF p_points IS NULL OR p_rebounds IS NULL OR p_assists IS NULL OR p_steals IS NULL OR p_blocks IS NULL
        OR p_points < 0 OR p_rebounds < 0 OR p_assists < 0 OR p_steals < 0 OR p_blocks < 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '技术统计数据不能为负数';
    END IF;

    SELECT COUNT(*)
    INTO v_exists
    FROM PlayerGameStats pgs
    JOIN Player p ON p.player_id = pgs.player_id
    JOIN Game g ON g.game_id = pgs.game_id
    WHERE pgs.player_id = p_player_id
      AND pgs.game_id = p_game_id;

    IF v_exists = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '未找到对应球员比赛技术统计记录';
    END IF;

    SELECT pgs.points,
           ap.current_team_id,
           g.home_team_id,
           g.away_team_id
    INTO v_old_points,
         v_player_team_id,
         v_home_team_id,
         v_away_team_id
    FROM PlayerGameStats pgs
    JOIN Player p ON p.player_id = pgs.player_id
    LEFT JOIN ActivePlayer ap ON ap.player_id = p.player_id
    JOIN Game g ON g.game_id = pgs.game_id
    WHERE pgs.player_id = p_player_id
      AND pgs.game_id = p_game_id;

    SET v_score_delta = p_points - v_old_points;

    IF v_player_team_id = v_home_team_id THEN
        UPDATE Game
        SET home_score = home_score + v_score_delta
        WHERE game_id = p_game_id;
    ELSEIF v_player_team_id = v_away_team_id THEN
        UPDATE Game
        SET away_score = away_score + v_score_delta
        WHERE game_id = p_game_id;
    ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '无法判断该球员所属球队，不能同步更新比赛比分';
    END IF;

    UPDATE PlayerGameStats
    SET points = p_points,
        rebounds = p_rebounds,
        assists = p_assists,
        steals = p_steals,
        blocks = p_blocks
    WHERE player_id = p_player_id
      AND game_id = p_game_id;
END//

DELIMITER ;

CREATE VIEW v_player_game_detail AS
SELECT
    p.player_id,
    p.name AS player_name,
    g.game_id,
    g.game_date,
    ht.team_name AS home_team,
    at.team_name AS away_team,
    pgs.points,
    pgs.rebounds,
    pgs.assists,
    pgs.steals,
    pgs.blocks
FROM PlayerGameStats pgs
JOIN Player p ON p.player_id = pgs.player_id
JOIN Game g ON g.game_id = pgs.game_id
JOIN Team ht ON ht.team_id = g.home_team_id
JOIN Team at ON at.team_id = g.away_team_id;
