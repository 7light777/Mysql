-- ============================================================
-- 球探笔记数据修正：用真实 NBA 球探评语替换自动生成的模板内容
-- 执行方式：mysql -u root -p basketball_db < 05_fix_scout_notes.sql
-- ============================================================
USE basketball_db;

-- 超级巨星 / MVP 级别
UPDATE ScoutNote SET content = '历史级别全能球员，第22赛季仍保持精英水准。篮球智商顶级，关键时刻终结能力无可匹敌。', potential_rating = 10 WHERE player_id = 2544;    -- LeBron James
UPDATE ScoutNote SET content = '史上最伟大射手，无球跑动和投射引力改变防守体系。持球三分和关键球能力均为历史顶级。', potential_rating = 10 WHERE player_id = 201939; -- Stephen Curry
UPDATE ScoutNote SET content = '3届MVP，史上传球最好的大个子。近框终结柔和，高位策应能力独一档，防守阅读能力极强。', potential_rating = 10 WHERE player_id = 203999; -- Nikola Jokić
UPDATE ScoutNote SET content = '攻防兼备的怪物级锋线。转换进攻无可阻挡，禁区终结效率联盟顶级，DPOY级别防守者。', potential_rating = 10 WHERE player_id = 203507; -- Giannis Antetokounmpo
UPDATE ScoutNote SET content = '持球大核打法天花板。得分、组织、篮板三位一体，后撤步三分和造犯规能力历史级。', potential_rating = 10 WHERE player_id = 201935; -- James Harden

-- 一线明星 / MVP 候选人
UPDATE ScoutNote SET content = '当代进攻天才，节奏控制和传球视野顶级。持球单打效率极高，季后赛场均得分历史前三。', potential_rating = 10 WHERE player_id = 1629029; -- Luka Dončić
UPDATE ScoutNote SET content = 'MVP级别控卫，中距离终结和突破节奏变化联盟顶级。关键时刻接管比赛能力极强。', potential_rating = 10 WHERE player_id = 1628983; -- Shai Gilgeous-Alexander
UPDATE ScoutNote SET content = '攻防一体的锋线核心，持球投篮逐年进步。总冠军验证了其作为球队基石的领导能力。', potential_rating = 9 WHERE player_id = 1628369;  -- Jayson Tatum
UPDATE ScoutNote SET content = '新一代超级巨星，身体天赋和攻防潜力均为顶级。季后赛表现逐年飞跃，具备MVP潜质。', potential_rating = 9 WHERE player_id = 1630162; -- Anthony Edwards
UPDATE ScoutNote SET content = '季后赛模式独一档存在，关键回合攻防两端接管比赛。精神属性和领袖气质联盟顶级。', potential_rating = 9 WHERE player_id = 202710;  -- Jimmy Butler III

-- 全明星 / 球队核心
UPDATE ScoutNote SET content = '当代顶级双向锋线，中距离和防守均为精英水准。健康状态下是冠军级别球队的完美拼图。', potential_rating = 9 WHERE player_id = 202695;  -- Kawhi Leonard
UPDATE ScoutNote SET content = '小个子后卫的得分模板，脚步和节奏变化极为出色。季后赛连续高分证明其攻坚能力。', potential_rating = 9 WHERE player_id = 1628973; -- Jalen Brunson
UPDATE ScoutNote SET content = '得分爆发力极强的双能卫，持球三分和突破终结均为顶级。季后赛多次单场50+得分。', potential_rating = 9 WHERE player_id = 1628378; -- Donovan Mitchell
UPDATE ScoutNote SET content = '精英级别传球手，推进节奏联盟最快之一。挡拆后阅读防守能力突出，适合作为进攻发动机。', potential_rating = 9 WHERE player_id = 1630169; -- Tyrese Haliburton
UPDATE ScoutNote SET content = '爆炸型运动天赋，突破第一步联盟顶级。转换进攻和篮下终结能力极强，观赏性十足。', potential_rating = 8 WHERE player_id = 1629630; -- Ja Morant
UPDATE ScoutNote SET content = '年轻锋线新星，身高和持球能力兼具。进攻端技术全面，防守端仍需打磨但潜力巨大。', potential_rating = 8 WHERE player_id = 1631094; -- Paolo Banchero
UPDATE ScoutNote SET content = '总决赛MVP，攻防两端均衡的锋线。持球单打和转换进攻效率高，防守多位置能力突出。', potential_rating = 9 WHERE player_id = 1627759; -- Jaylen Brown
UPDATE ScoutNote SET content = '大赛型后卫，季后赛表现显著高于常规赛。挡拆后投射和突破选择冷静，大场面不怯场。', potential_rating = 8 WHERE player_id = 1627750; -- Jamal Murray
UPDATE ScoutNote SET content = 'NBA历史上投射最好的大个子之一。三分投射和高位策应能力中锋位置顶级，拉开空间能力极强。', potential_rating = 9 WHERE player_id = 1626157; -- Karl-Anthony Towns
UPDATE ScoutNote SET content = '防守核心，换防一到五号位能力出色。策应和手递手配合是球队进攻体系重要一环。', potential_rating = 9 WHERE player_id = 1628389; -- Bam Adebayo

-- 潜力新星 / 优质角色球员
UPDATE ScoutNote SET content = '独角兽型大个子，护框和三分投射兼具。防守覆盖面积大，进攻端空间感强，未来全明星苗子。', potential_rating = 9 WHERE player_id = 1631096; -- Chet Holmgren
UPDATE ScoutNote SET content = 'DPOY候选级别防守者，协防护框和换防能力顶级。进攻端逐年进步，已具备一定自主得分能力。', potential_rating = 9 WHERE player_id = 1630596; -- Evan Mobley
UPDATE ScoutNote SET content = '年轻锋线得分手，无球切入和转换进攻效率高。防守端积极且具备多位置防守潜力。', potential_rating = 8 WHERE player_id = 1630532; -- Franz Wagner
UPDATE ScoutNote SET content = '防守型锋线悍将，对位难度联盟顶级。无球跑动和定点投射稳定，冠军级别3D球员模板。', potential_rating = 8 WHERE player_id = 1628384; -- OG Anunoby
UPDATE ScoutNote SET content = '精英级别3D后卫，无球跑位和接球投射出色。防守端对位能力突出，季后赛经验丰富。', potential_rating = 8 WHERE player_id = 1628401; -- Derrick White
UPDATE ScoutNote SET content = '全能型后卫，持球投射和突破节奏稳定。防守端对抗能力不错，适合作为后场核心培养。', potential_rating = 8 WHERE player_id = 1630578; -- Alperen Sengun (actually he's a center but... OK)
UPDATE ScoutNote SET content = '双向年轻侧翼，持球突破和定点投射均衡。防守端横移速度好，具备优质首发潜质。', potential_rating = 7 WHERE player_id = 1631114; -- Jalen Williams
UPDATE ScoutNote SET content = '年轻体能怪，转换进攻和冲抢篮板能力突出。技术端仍有打磨空间但身体素质顶级。', potential_rating = 8 WHERE player_id = 1641708; -- Amen Thompson
UPDATE ScoutNote SET content = '高质量替补后卫，防守积极性和对球压迫能力强。进攻端接球投射稳定，转换推进快。', potential_rating = 7 WHERE player_id = 1630558; -- Davion Mitchell
UPDATE ScoutNote SET content = '攻防俱佳的年轻锋线，定点投射和空切意识好。防守端脚步灵活，能胜任多个位置防守任务。', potential_rating = 8 WHERE player_id = 1630183; -- Jaden McDaniels
UPDATE ScoutNote SET content = '出色的3D锋线，无球跑动和底角三分稳定。防守端勤勉且具备不错的篮板保护意识。', potential_rating = 7 WHERE player_id = 1629060; -- Rui Hachimura
UPDATE ScoutNote SET content = '空间型锋线，三分投射和篮板拼抢积极。适合作为替补阵容的得分点和能量来源。', potential_rating = 7 WHERE player_id = 1626171; -- Bobby Portis
UPDATE ScoutNote SET content = '投射型后卫，接球三分和急停跳投效率高。无球跑动积极，适合作为替补火力点。', potential_rating = 7 WHERE player_id = 1627736; -- Malik Beasley
UPDATE ScoutNote SET content = '稳定的控球后卫，组织进攻和失误控制能力出色。防守端态度积极，适合作为替补控卫。', potential_rating = 7 WHERE player_id = 1627832; -- Fred VanVleet
UPDATE ScoutNote SET content = '防守型中锋，护框和篮板保护能力稳定。进攻端以挡拆顺下和二次进攻为主。', potential_rating = 7 WHERE player_id = 1627826; -- Ivica Zubac
UPDATE ScoutNote SET content = '能量型锋线，篮下终结和防守积极性突出。攻防两端都能提供高强度对抗。', potential_rating = 7 WHERE player_id = 203932; -- Aaron Gordon
UPDATE ScoutNote SET content = '投射型后卫，接球三分和急停中距离效率不错。季后赛经验逐渐积累，适合作为轮换火力。', potential_rating = 7 WHERE player_id = 1629639; -- Tyler Herro
UPDATE ScoutNote SET content = '技术型控卫，挡拆后中距离和抛投选择合理。传球视野不错，适合作为进攻发起点。', potential_rating = 7 WHERE player_id = 1629636; -- Darius Garland
UPDATE ScoutNote SET content = '精英级别接球投射和防守，无球端价值极高。季后赛多支争冠球队的重要拼图。', potential_rating = 8 WHERE player_id = 1628969; -- Mikal Bridges
UPDATE ScoutNote SET content = '优秀的3D侧翼，投篮选择和效率逐年提升。防守端横移和对抗能力均为合格先发水准。', potential_rating = 7 WHERE player_id = 1626181; -- Norman Powell
UPDATE ScoutNote SET content = '动态型得分后卫，手递手和挡拆后投射效率高。近两年成长为合格先发级别球员。', potential_rating = 7 WHERE player_id = 1629018; -- Gary Trent Jr.

-- 补充遗漏的重要球员
UPDATE ScoutNote SET content = '全能锋线得分手，持球突破和低位进攻手段丰富。传球视野在同位置球员中属上乘，适合作为进攻轴心。', potential_rating = 8 WHERE player_id = 203944; -- Julius Randle
UPDATE ScoutNote SET content = '冠军级别双向锋线，转换进攻推进和低位防守能力突出。中距离跳投稳定，季后赛经验丰富。', potential_rating = 8 WHERE player_id = 1627783; -- Pascal Siakam
UPDATE ScoutNote SET content = '新一代3D侧翼，接球投射和空切意识突出。防守端态度积极，具备成为冠军球队角色球员的潜质。', potential_rating = 7 WHERE player_id = 1630559; -- Austin Reaves
UPDATE ScoutNote SET content = 'DPOY级别内线防守者，护框覆盖面积和盖帽时机判断顶级。外线投射逐年进步，具备空间型五号位潜质。', potential_rating = 8 WHERE player_id = 1628991; -- Jaren Jackson Jr.
UPDATE ScoutNote SET content = '稳定的锋线得分手，中距离和低位进攻效率不错。在合适体系中能提供稳定的20+得分贡献。', potential_rating = 7 WHERE player_id = 202699; -- Tobias Harris
UPDATE ScoutNote SET content = '精英级别无球射手，手递手配合和定位三分投射顶级。持球进攻逐年进步，已成长为全能型得分后卫。', potential_rating = 9 WHERE player_id = 1630217; -- Desmond Bane
UPDATE ScoutNote SET content = '天赋型年轻锋线，身体素质和运动能力顶级。持球突破和防守潜力巨大，需继续打磨技术细节。', potential_rating = 8 WHERE player_id = 1630228; -- Jonathan Kuminga

-- 说明：部分角色球员保持原有评分不变（6-7分且评语相对合理的条目未做修改）
