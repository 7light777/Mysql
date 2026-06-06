USE basketball_db;

-- 回填 2024-25 NBA Playoffs 球员展示位置。
-- 说明：用于页面展示，统一使用 PG / SG / SF / PF / C，避免页面出现空白或“未录入”。

UPDATE Player SET position = 'SF' WHERE player_id = 2544; -- LeBron James
UPDATE Player SET position = 'SF' WHERE player_id = 200782; -- P.J. Tucker
UPDATE Player SET position = 'C' WHERE player_id = 201143; -- Al Horford
UPDATE Player SET position = 'PG' WHERE player_id = 201144; -- Mike Conley
UPDATE Player SET position = 'PG' WHERE player_id = 201145; -- Jeff Green
UPDATE Player SET position = 'PG' WHERE player_id = 201566; -- Russell Westbrook
UPDATE Player SET position = 'C' WHERE player_id = 201572; -- Brook Lopez
UPDATE Player SET position = 'SF' WHERE player_id = 201587; -- Nicolas Batum
UPDATE Player SET position = 'C' WHERE player_id = 201599; -- DeAndre Jordan
UPDATE Player SET position = 'PG' WHERE player_id = 201935; -- James Harden
UPDATE Player SET position = 'PG' WHERE player_id = 201939; -- Stephen Curry
UPDATE Player SET position = 'C' WHERE player_id = 201949; -- James Johnson
UPDATE Player SET position = 'SG' WHERE player_id = 201950; -- Jrue Holiday
UPDATE Player SET position = 'PF' WHERE player_id = 201988; -- Patty Mills
UPDATE Player SET position = 'C' WHERE player_id = 202684; -- Tristan Thompson
UPDATE Player SET position = 'SF' WHERE player_id = 202692; -- Alec Burks
UPDATE Player SET position = 'SF' WHERE player_id = 202695; -- Kawhi Leonard
UPDATE Player SET position = 'C' WHERE player_id = 202699; -- Tobias Harris
UPDATE Player SET position = 'C' WHERE player_id = 202709; -- Cory Joseph
UPDATE Player SET position = 'SF' WHERE player_id = 202710; -- Jimmy Butler III
UPDATE Player SET position = 'PG' WHERE player_id = 203081; -- Damian Lillard
UPDATE Player SET position = 'PF' WHERE player_id = 203110; -- Draymond Green
UPDATE Player SET position = 'PF' WHERE player_id = 203458; -- Alex Len
UPDATE Player SET position = 'SG' WHERE player_id = 203471; -- Dennis Schröder
UPDATE Player SET position = 'C' WHERE player_id = 203484; -- Kentavious Caldwell-Pope
UPDATE Player SET position = 'C' WHERE player_id = 203497; -- Rudy Gobert
UPDATE Player SET position = 'PG' WHERE player_id = 203500; -- Steven Adams
UPDATE Player SET position = 'SG' WHERE player_id = 203501; -- Tim Hardaway Jr.
UPDATE Player SET position = 'PF' WHERE player_id = 203507; -- Giannis Antetokounmpo
UPDATE Player SET position = 'C' WHERE player_id = 203914; -- Gary Harris
UPDATE Player SET position = 'PF' WHERE player_id = 203932; -- Aaron Gordon
UPDATE Player SET position = 'SF' WHERE player_id = 203937; -- Kyle Anderson
UPDATE Player SET position = 'PF' WHERE player_id = 203944; -- Julius Randle
UPDATE Player SET position = 'SF' WHERE player_id = 203952; -- Andrew Wiggins
UPDATE Player SET position = 'SF' WHERE player_id = 203992; -- Bogdan Bogdanović
UPDATE Player SET position = 'C' WHERE player_id = 203999; -- Nikola Jokić
UPDATE Player SET position = 'SG' WHERE player_id = 204001; -- Kristaps Porziņģis
UPDATE Player SET position = 'PG' WHERE player_id = 204456; -- T.J. McConnell
UPDATE Player SET position = 'PF' WHERE player_id = 1626153; -- Delon Wright
UPDATE Player SET position = 'C' WHERE player_id = 1626157; -- Karl-Anthony Towns
UPDATE Player SET position = 'SG' WHERE player_id = 1626166; -- Cameron Payne
UPDATE Player SET position = 'C' WHERE player_id = 1626167; -- Myles Turner
UPDATE Player SET position = 'SG' WHERE player_id = 1626171; -- Bobby Portis
UPDATE Player SET position = 'SF' WHERE player_id = 1626172; -- Kevon Looney
UPDATE Player SET position = 'SG' WHERE player_id = 1626181; -- Norman Powell
UPDATE Player SET position = 'SF' WHERE player_id = 1626192; -- Pat Connaughton
UPDATE Player SET position = 'SF' WHERE player_id = 1627732; -- Ben Simmons
UPDATE Player SET position = 'SG' WHERE player_id = 1627736; -- Malik Beasley
UPDATE Player SET position = 'C' WHERE player_id = 1627739; -- Kris Dunn
UPDATE Player SET position = 'SG' WHERE player_id = 1627741; -- Buddy Hield
UPDATE Player SET position = 'PG' WHERE player_id = 1627750; -- Jamal Murray
UPDATE Player SET position = 'SF' WHERE player_id = 1627752; -- Taurean Prince
UPDATE Player SET position = 'SG' WHERE player_id = 1627759; -- Jaylen Brown
UPDATE Player SET position = 'PG' WHERE player_id = 1627780; -- Gary Payton II
UPDATE Player SET position = 'PF' WHERE player_id = 1627783; -- Pascal Siakam
UPDATE Player SET position = 'SG' WHERE player_id = 1627826; -- Ivica Zubac
UPDATE Player SET position = 'SF' WHERE player_id = 1627827; -- Dorian Finney-Smith
UPDATE Player SET position = 'SF' WHERE player_id = 1627832; -- Fred VanVleet
UPDATE Player SET position = 'C' WHERE player_id = 1627884; -- Derrick Jones Jr.
UPDATE Player SET position = 'SG' WHERE player_id = 1627936; -- Alex Caruso
UPDATE Player SET position = 'SF' WHERE player_id = 1628369; -- Jayson Tatum
UPDATE Player SET position = 'SG' WHERE player_id = 1628371; -- Jonathan Isaac
UPDATE Player SET position = 'SG' WHERE player_id = 1628378; -- Donovan Mitchell
UPDATE Player SET position = 'C' WHERE player_id = 1628379; -- Luke Kennard
UPDATE Player SET position = 'C' WHERE player_id = 1628384; -- OG Anunoby
UPDATE Player SET position = 'SG' WHERE player_id = 1628386; -- Jarrett Allen
UPDATE Player SET position = 'C' WHERE player_id = 1628389; -- Bam Adebayo
UPDATE Player SET position = 'C' WHERE player_id = 1628392; -- Isaiah Hartenstein
UPDATE Player SET position = 'C' WHERE player_id = 1628396; -- Tony Bradley
UPDATE Player SET position = 'PF' WHERE player_id = 1628398; -- Kyle Kuzma
UPDATE Player SET position = 'SG' WHERE player_id = 1628401; -- Derrick White
UPDATE Player SET position = 'C' WHERE player_id = 1628404; -- Josh Hart
UPDATE Player SET position = 'PG' WHERE player_id = 1628415; -- Dillon Brooks
UPDATE Player SET position = 'PF' WHERE player_id = 1628418; -- Thomas Bryant
UPDATE Player SET position = 'SF' WHERE player_id = 1628427; -- Vlatko Čančar
UPDATE Player SET position = 'SG' WHERE player_id = 1628436; -- Luke Kornet
UPDATE Player SET position = 'SF' WHERE player_id = 1628467; -- Maxi Kleber
UPDATE Player SET position = 'PG' WHERE player_id = 1628470; -- Torrey Craig
UPDATE Player SET position = 'PF' WHERE player_id = 1628963; -- Marvin Bagley III
UPDATE Player SET position = 'C' WHERE player_id = 1628969; -- Mikal Bridges
UPDATE Player SET position = 'PG' WHERE player_id = 1628973; -- Jalen Brunson
UPDATE Player SET position = 'SG' WHERE player_id = 1628976; -- Wendell Carter Jr.
UPDATE Player SET position = 'PF' WHERE player_id = 1628978; -- Donte DiVincenzo
UPDATE Player SET position = 'PG' WHERE player_id = 1628983; -- Shai Gilgeous-Alexander
UPDATE Player SET position = 'PF' WHERE player_id = 1628988; -- Aaron Holiday
UPDATE Player SET position = 'SG' WHERE player_id = 1628991; -- Jaren Jackson Jr.
UPDATE Player SET position = 'PG' WHERE player_id = 1628995; -- Kevin Knox II
UPDATE Player SET position = 'PF' WHERE player_id = 1629003; -- Shake Milton
UPDATE Player SET position = 'PF' WHERE player_id = 1629008; -- Michael Porter Jr.
UPDATE Player SET position = 'SG' WHERE player_id = 1629011; -- Mitchell Robinson
UPDATE Player SET position = 'PF' WHERE player_id = 1629013; -- Landry Shamet
UPDATE Player SET position = 'PF' WHERE player_id = 1629018; -- Gary Trent Jr.
UPDATE Player SET position = 'PG' WHERE player_id = 1629020; -- Jarred Vanderbilt
UPDATE Player SET position = 'SG' WHERE player_id = 1629026; -- Kenrich Williams
UPDATE Player SET position = 'PG' WHERE player_id = 1629029; -- Luka Dončić
UPDATE Player SET position = 'PF' WHERE player_id = 1629048; -- Goga Bitadze
UPDATE Player SET position = 'PG' WHERE player_id = 1629060; -- Rui Hachimura
UPDATE Player SET position = 'SG' WHERE player_id = 1629111; -- Jock Landale
UPDATE Player SET position = 'PG' WHERE player_id = 1629130; -- Duncan Robinson
UPDATE Player SET position = 'SG' WHERE player_id = 1629216; -- Gabe Vincent
UPDATE Player SET position = 'C' WHERE player_id = 1629234; -- Drew Eubanks
UPDATE Player SET position = 'SF' WHERE player_id = 1629312; -- Haywood Highsmith
UPDATE Player SET position = 'SG' WHERE player_id = 1629614; -- Andrew Nembhard
UPDATE Player SET position = 'PF' WHERE player_id = 1629618; -- Jalen Pickett
UPDATE Player SET position = 'SF' WHERE player_id = 1629622; -- Max Strus
UPDATE Player SET position = 'PG' WHERE player_id = 1629630; -- Ja Morant
UPDATE Player SET position = 'SG' WHERE player_id = 1629631; -- De''Andre Hunter
UPDATE Player SET position = 'SG' WHERE player_id = 1629636; -- Darius Garland
UPDATE Player SET position = 'SF' WHERE player_id = 1629637; -- Jaxson Hayes
UPDATE Player SET position = 'PF' WHERE player_id = 1629638; -- Nickeil Alexander-Walker
UPDATE Player SET position = 'C' WHERE player_id = 1629639; -- Tyler Herro
UPDATE Player SET position = 'PF' WHERE player_id = 1629643; -- Chuma Okeke
UPDATE Player SET position = 'PG' WHERE player_id = 1629645; -- Kevin Porter Jr.
UPDATE Player SET position = 'SF' WHERE player_id = 1629652; -- Luguentz Dort
UPDATE Player SET position = 'PG' WHERE player_id = 1629660; -- Ty Jerome
UPDATE Player SET position = 'C' WHERE player_id = 1629674; -- Neemias Queta
UPDATE Player SET position = 'PG' WHERE player_id = 1629675; -- Naz Reid
UPDATE Player SET position = 'PF' WHERE player_id = 1629723; -- John Konchar
UPDATE Player SET position = 'SG' WHERE player_id = 1629731; -- Dean Wade
UPDATE Player SET position = 'PG' WHERE player_id = 1629750; -- Javonte Green
UPDATE Player SET position = 'SG' WHERE player_id = 1630162; -- Anthony Edwards
UPDATE Player SET position = 'SF' WHERE player_id = 1630167; -- Obi Toppin
UPDATE Player SET position = 'PG' WHERE player_id = 1630169; -- Tyrese Haliburton
UPDATE Player SET position = 'SG' WHERE player_id = 1630171; -- Isaac Okoro
UPDATE Player SET position = 'PF' WHERE player_id = 1630173; -- Precious Achiuwa
UPDATE Player SET position = 'SF' WHERE player_id = 1630174; -- Aaron Nesmith
UPDATE Player SET position = 'PG' WHERE player_id = 1630175; -- Cole Anthony
UPDATE Player SET position = 'PF' WHERE player_id = 1630183; -- Jaden McDaniels
UPDATE Player SET position = 'SG' WHERE player_id = 1630191; -- Isaiah Stewart
UPDATE Player SET position = 'SF' WHERE player_id = 1630192; -- Zeke Nnaji
UPDATE Player SET position = 'C' WHERE player_id = 1630194; -- Paul Reed
UPDATE Player SET position = 'PF' WHERE player_id = 1630198; -- Isaiah Joe
UPDATE Player SET position = 'SF' WHERE player_id = 1630202; -- Payton Pritchard
UPDATE Player SET position = 'PG' WHERE player_id = 1630205; -- Lamar Stevens
UPDATE Player SET position = 'C' WHERE player_id = 1630214; -- Xavier Tillman
UPDATE Player SET position = 'SF' WHERE player_id = 1630217; -- Desmond Bane
UPDATE Player SET position = 'C' WHERE player_id = 1630224; -- Jalen Green
UPDATE Player SET position = 'PF' WHERE player_id = 1630228; -- Jonathan Kuminga
UPDATE Player SET position = 'SG' WHERE player_id = 1630241; -- Sam Merrill
UPDATE Player SET position = 'SG' WHERE player_id = 1630296; -- Braxton Key
UPDATE Player SET position = 'SG' WHERE player_id = 1630311; -- Pat Spencer
UPDATE Player SET position = 'SF' WHERE player_id = 1630532; -- Franz Wagner
UPDATE Player SET position = 'PG' WHERE player_id = 1630540; -- Miles McBride
UPDATE Player SET position = 'SG' WHERE player_id = 1630541; -- Moses Moody
UPDATE Player SET position = 'PG' WHERE player_id = 1630545; -- Terrence Shannon Jr.
UPDATE Player SET position = 'PF' WHERE player_id = 1630558; -- Davion Mitchell
UPDATE Player SET position = 'C' WHERE player_id = 1630559; -- Austin Reaves
UPDATE Player SET position = 'PF' WHERE player_id = 1630568; -- Luka Garza
UPDATE Player SET position = 'PF' WHERE player_id = 1630573; -- Sam Hauser
UPDATE Player SET position = 'C' WHERE player_id = 1630574; -- Ariel Hukporti
UPDATE Player SET position = 'PF' WHERE player_id = 1630578; -- Alperen Sengun
UPDATE Player SET position = 'C' WHERE player_id = 1630579; -- Jericho Sims
UPDATE Player SET position = 'PF' WHERE player_id = 1630583; -- Santi Aldama
UPDATE Player SET position = 'PG' WHERE player_id = 1630590; -- Scotty Pippen Jr.
UPDATE Player SET position = 'PG' WHERE player_id = 1630595; -- Cade Cunningham
UPDATE Player SET position = 'SG' WHERE player_id = 1630596; -- Evan Mobley
UPDATE Player SET position = 'SG' WHERE player_id = 1630598; -- Aaron Wiggins
UPDATE Player SET position = 'SG' WHERE player_id = 1630611; -- Gui Santos
UPDATE Player SET position = 'PF' WHERE player_id = 1630643; -- Jay Huff
UPDATE Player SET position = 'SF' WHERE player_id = 1630692; -- Jordan Goodwin
UPDATE Player SET position = 'PF' WHERE player_id = 1631094; -- Paolo Banchero
UPDATE Player SET position = 'PG' WHERE player_id = 1631095; -- Jabari Smith Jr.
UPDATE Player SET position = 'C' WHERE player_id = 1631096; -- Chet Holmgren
UPDATE Player SET position = 'SG' WHERE player_id = 1631097; -- Bennedict Mathurin
UPDATE Player SET position = 'PG' WHERE player_id = 1631105; -- Jalen Duren
UPDATE Player SET position = 'SG' WHERE player_id = 1631106; -- Tari Eason
UPDATE Player SET position = 'SF' WHERE player_id = 1631107; -- Nikola Jović
UPDATE Player SET position = 'SF' WHERE player_id = 1631114; -- Jalen Williams
UPDATE Player SET position = 'C' WHERE player_id = 1631119; -- Jaylin Williams
UPDATE Player SET position = 'PG' WHERE player_id = 1631120; -- JD Davison
UPDATE Player SET position = 'C' WHERE player_id = 1631124; -- Julian Strawther
UPDATE Player SET position = 'PF' WHERE player_id = 1631128; -- Christian Braun
UPDATE Player SET position = 'SF' WHERE player_id = 1631157; -- Ryan Rollins
UPDATE Player SET position = 'C' WHERE player_id = 1631159; -- Leonard Miller
UPDATE Player SET position = 'C' WHERE player_id = 1631169; -- Josh Minott
UPDATE Player SET position = 'PG' WHERE player_id = 1631170; -- Jaime Jaquez Jr.
UPDATE Player SET position = 'SF' WHERE player_id = 1631172; -- Ousmane Dieng
UPDATE Player SET position = 'SF' WHERE player_id = 1631212; -- Peyton Watson
UPDATE Player SET position = 'SG' WHERE player_id = 1631216; -- Caleb Houstan
UPDATE Player SET position = 'PF' WHERE player_id = 1631218; -- Trayce Jackson-Davis
UPDATE Player SET position = 'SG' WHERE player_id = 1631246; -- Vince Williams Jr.
UPDATE Player SET position = 'PF' WHERE player_id = 1631248; -- Baylor Scheierman
UPDATE Player SET position = 'PG' WHERE player_id = 1631260; -- AJ Green
UPDATE Player SET position = 'SG' WHERE player_id = 1631466; -- Nate Williams
UPDATE Player SET position = 'PF' WHERE player_id = 1641708; -- Amen Thompson
UPDATE Player SET position = 'C' WHERE player_id = 1641709; -- Ausar Thompson
UPDATE Player SET position = 'PG' WHERE player_id = 1641710; -- Anthony Black
UPDATE Player SET position = 'PG' WHERE player_id = 1641715; -- Cam Whitmore
UPDATE Player SET position = 'SG' WHERE player_id = 1641716; -- Jarace Walker
UPDATE Player SET position = 'SG' WHERE player_id = 1641717; -- Cason Wallace
UPDATE Player SET position = 'C' WHERE player_id = 1641724; -- Jett Howard
UPDATE Player SET position = 'PF' WHERE player_id = 1641738; -- Kobe Brown
UPDATE Player SET position = 'PG' WHERE player_id = 1641740; -- Jaylen Clark
UPDATE Player SET position = 'C' WHERE player_id = 1641744; -- Zach Edey
UPDATE Player SET position = 'PF' WHERE player_id = 1641748; -- Andre Jackson Jr.
UPDATE Player SET position = 'PF' WHERE player_id = 1641753; -- Chris Livingston
UPDATE Player SET position = 'SF' WHERE player_id = 1641757; -- Jordan Miller
UPDATE Player SET position = 'C' WHERE player_id = 1641764; -- Brandin Podziemski
UPDATE Player SET position = 'SF' WHERE player_id = 1641767; -- Ben Sheppard
UPDATE Player SET position = 'PG' WHERE player_id = 1641775; -- Jordan Walsh
UPDATE Player SET position = 'PF' WHERE player_id = 1641783; -- Tristan da Silva
UPDATE Player SET position = 'C' WHERE player_id = 1641794; -- Dillon Jones
UPDATE Player SET position = 'SG' WHERE player_id = 1641796; -- Pelle Larsson
UPDATE Player SET position = 'SG' WHERE player_id = 1641816; -- Hunter Tyson
UPDATE Player SET position = 'SF' WHERE player_id = 1641842; -- Ronald Holland II
UPDATE Player SET position = 'C' WHERE player_id = 1641854; -- Craig Porter Jr.
UPDATE Player SET position = 'SG' WHERE player_id = 1642261; -- Dalton Knecht
UPDATE Player SET position = 'PF' WHERE player_id = 1642263; -- Reed Sheppard
UPDATE Player SET position = 'PG' WHERE player_id = 1642265; -- Rob Dillingham
UPDATE Player SET position = 'SG' WHERE player_id = 1642276; -- Kel''el Ware
UPDATE Player SET position = 'SF' WHERE player_id = 1642277; -- Johnny Furphy
UPDATE Player SET position = 'PF' WHERE player_id = 1642278; -- Tyler Kolek
UPDATE Player SET position = 'SG' WHERE player_id = 1642281; -- Jaylon Tyson
UPDATE Player SET position = 'PG' WHERE player_id = 1642349; -- Ajay Mitchell
UPDATE Player SET position = 'SF' WHERE player_id = 1642352; -- Keshad Johnson
UPDATE Player SET position = 'PF' WHERE player_id = 1642353; -- Cam Christie
UPDATE Player SET position = 'PG' WHERE player_id = 1642355; -- Bronny James
UPDATE Player SET position = 'C' WHERE player_id = 1642359; -- Pacôme Dadiet
UPDATE Player SET position = 'SG' WHERE player_id = 1642366; -- Quinten Post

-- Updated player position rows: 219
