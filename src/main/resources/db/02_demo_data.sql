USE basketball_db;

INSERT INTO Team (team_id, team_name, city, conference) VALUES
(1, 'Lakers', 'Los Angeles', 'West'),
(2, 'Warriors', 'San Francisco', 'West'),
(3, 'Celtics', 'Boston', 'East'),
(4, 'Heat', 'Miami', 'East');

INSERT INTO Player (player_id, name, birth_date, position, height, weight) VALUES
(1, 'LeBron James', '1984-12-30', 'SF', 2.06, 113.4),
(2, 'Anthony Davis', '1993-03-11', 'PF', 2.08, 115.7),
(3, 'Stephen Curry', '1988-03-14', 'PG', 1.88, 83.9),
(4, 'Klay Thompson', '1990-02-08', 'SG', 1.98, 97.5),
(5, 'Jayson Tatum', '1998-03-03', 'SF', 2.03, 95.3),
(6, 'Jimmy Butler', '1989-09-14', 'SF', 2.01, 104.3),
(7, 'Kobe Bryant', '1978-08-23', 'SG', 1.98, 96.2),
(8, 'Dwyane Wade', '1982-01-17', 'SG', 1.93, 99.8);

INSERT INTO ActivePlayer (player_id, current_team_id, jersey_number, years_exp) VALUES
(1, 1, 23, 21),
(2, 1, 3, 12),
(3, 2, 30, 15),
(4, 2, 11, 13),
(5, 3, 0, 7),
(6, 4, 22, 13);

INSERT INTO RetiredPlayer (player_id, retired_year, hall_of_fame) VALUES
(7, 2016, TRUE),
(8, 2019, TRUE);

INSERT INTO Game (game_id, game_date, home_team_id, away_team_id, home_score, away_score) VALUES
(1, '2026-04-01', 1, 2, 112, 108),
(2, '2026-04-03', 3, 4, 104, 99),
(3, '2026-04-06', 2, 3, 118, 121),
(4, '2026-04-09', 4, 1, 96, 102);

INSERT INTO PlayerGameStats (player_id, game_id, points, rebounds, assists, steals, blocks) VALUES
(1, 1, 28, 8, 9, 2, 1),
(2, 1, 24, 12, 3, 1, 3),
(3, 1, 31, 5, 7, 2, 0),
(4, 1, 19, 3, 2, 1, 1),
(5, 2, 33, 10, 5, 1, 1),
(6, 2, 27, 7, 6, 3, 0),
(8, 2, 18, 4, 5, 2, 1),
(3, 3, 36, 4, 8, 1, 0),
(4, 3, 22, 6, 3, 1, 1),
(5, 3, 30, 9, 4, 2, 2),
(6, 4, 25, 8, 7, 2, 1),
(1, 4, 29, 11, 8, 1, 2),
(2, 4, 21, 13, 2, 0, 4);

INSERT INTO ScoutNote (player_id, note_date, content, potential_rating) VALUES
(3, '2026-04-10', '外线投射稳定，关键球处理能力突出。', 9),
(5, '2026-04-10', '锋线持球能力优秀，攻防两端都具备核心潜力。', 9),
(6, '2026-04-11', '防守压迫性强，适合作为季后赛关键球员。', 8);

INSERT INTO UserAccount (username, password, display_name, role) VALUES
('admin', '123456', '管理员', 'ADMIN'),
('scout', '123456', '球探用户', 'SCOUT'),
('viewer', '123456', '访客用户', 'VIEWER');
