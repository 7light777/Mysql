DROP DATABASE IF EXISTS basketball_db;
CREATE DATABASE basketball_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE basketball_db;

DROP TABLE IF EXISTS ScoutNote;
DROP TABLE IF EXISTS PlayerGameStats;
DROP TABLE IF EXISTS RetiredPlayer;
DROP TABLE IF EXISTS ActivePlayer;
DROP TABLE IF EXISTS Game;
DROP TABLE IF EXISTS Player;
DROP TABLE IF EXISTS Team;
DROP TABLE IF EXISTS UserAccount;

CREATE TABLE Team (
    team_id         INT PRIMARY KEY AUTO_INCREMENT  COMMENT '球队ID（主键）',
    team_name       VARCHAR(50) NOT NULL            COMMENT '球队名称',
    city            VARCHAR(30)                     COMMENT '所在城市',
    conference      ENUM('East', 'West')            COMMENT '分区（东部/西部）'
) COMMENT='球队信息表';

CREATE TABLE Player (
    player_id       INT PRIMARY KEY AUTO_INCREMENT  COMMENT '球员ID（主键）',
    name            VARCHAR(50) NOT NULL            COMMENT '球员姓名',
    birth_date      DATE                            COMMENT '出生日期',
    position        ENUM('PG','SG','SF','PF','C')   COMMENT '场上位置',
    height          DECIMAL(3,2)                    COMMENT '身高（米）',
    weight          DECIMAL(4,1)                    COMMENT '体重（公斤）'
) COMMENT='球员基本信息表（父表）';

CREATE TABLE ActivePlayer (
    player_id       INT PRIMARY KEY                 COMMENT '球员ID（外键，参照Player表）',
    current_team_id INT                             COMMENT '当前效力球队ID（外键）',
    jersey_number   INT                             COMMENT '球衣号码',
    years_exp       INT                             COMMENT '球龄（年）',
    CONSTRAINT fk_active_player_player FOREIGN KEY (player_id) REFERENCES Player(player_id) ON DELETE CASCADE,
    CONSTRAINT fk_active_player_team FOREIGN KEY (current_team_id) REFERENCES Team(team_id)
) COMMENT='现役球员表（Player的子表）';

CREATE TABLE RetiredPlayer (
    player_id       INT PRIMARY KEY                 COMMENT '球员ID（外键，参照Player表）',
    retired_year    YEAR                            COMMENT '退役年份',
    hall_of_fame    BOOLEAN DEFAULT FALSE           COMMENT '是否入选名人堂',
    CONSTRAINT fk_retired_player_player FOREIGN KEY (player_id) REFERENCES Player(player_id) ON DELETE CASCADE
) COMMENT='退役球员表（Player的子表）';

CREATE TABLE Game (
    game_id         INT PRIMARY KEY AUTO_INCREMENT  COMMENT '比赛ID（主键）',
    game_date       DATE NOT NULL                   COMMENT '比赛日期',
    home_team_id    INT                             COMMENT '主队ID（外键）',
    away_team_id    INT                             COMMENT '客队ID（外键）',
    home_score      INT                             COMMENT '主场得分',
    away_score      INT                             COMMENT '客场得分',
    CONSTRAINT fk_game_home_team FOREIGN KEY (home_team_id) REFERENCES Team(team_id),
    CONSTRAINT fk_game_away_team FOREIGN KEY (away_team_id) REFERENCES Team(team_id)
) COMMENT='比赛信息表';

CREATE TABLE PlayerGameStats (
    player_id       INT                             COMMENT '球员ID（外键）',
    game_id         INT                             COMMENT '比赛ID（外键）',
    points          INT DEFAULT 0                   COMMENT '得分',
    rebounds        INT DEFAULT 0                   COMMENT '篮板',
    assists         INT DEFAULT 0                   COMMENT '助攻',
    steals          INT DEFAULT 0                   COMMENT '抢断',
    blocks          INT DEFAULT 0                   COMMENT '盖帽',
    PRIMARY KEY (player_id, game_id),
    CONSTRAINT fk_stats_player FOREIGN KEY (player_id) REFERENCES Player(player_id),
    CONSTRAINT fk_stats_game FOREIGN KEY (game_id) REFERENCES Game(game_id)
) COMMENT='球员单场比赛技术统计表';

CREATE TABLE ScoutNote (
    note_id          INT PRIMARY KEY AUTO_INCREMENT COMMENT '笔记ID（主键）',
    player_id        INT                            COMMENT '球员ID（外键）',
    note_date        DATE DEFAULT (CURRENT_DATE)    COMMENT '记录日期',
    content          TEXT                           COMMENT '笔记内容',
    potential_rating TINYINT                        COMMENT '潜力评分（1-10分）',
    CHECK (potential_rating BETWEEN 1 AND 10),
    CONSTRAINT fk_scout_note_player FOREIGN KEY (player_id) REFERENCES Player(player_id)
) COMMENT='个人球探笔记表';

CREATE TABLE UserAccount (
    user_id       INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID（主键）',
    username      VARCHAR(50) NOT NULL UNIQUE    COMMENT '登录用户名',
    password      VARCHAR(100) NOT NULL          COMMENT '登录密码（课程演示使用明文）',
    display_name  VARCHAR(50) NOT NULL           COMMENT '显示名称',
    role          VARCHAR(20) NOT NULL           COMMENT '角色：ADMIN/SCOUT/VIEWER'
) COMMENT='系统登录用户表';
