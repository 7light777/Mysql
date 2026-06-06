# Basketball Management System

篮球比赛数据管理系统，面向数据库期末工程演示。项目包含 Spring Boot REST API、Thymeleaf 后台页面、MySQL 脚本，以及事务、触发器、存储过程、视图四类数据库操作。

## 技术栈

- Java 17
- Spring Boot 3.3.x
- Thymeleaf
- MyBatis
- MySQL 8.0
- Maven

## 数据库初始化

数据库名：`basketball_db`

脚本执行顺序：

```bash
mysql -u root -p < src/main/resources/db/01_schema.sql
mysql -u root -p basketball_db < src/main/resources/db/02_demo_data.sql
mysql -u root -p basketball_db < src/main/resources/db/03_trigger_procedure_view.sql
```

如果使用 `basketball-data-loader` 生成的真实季后赛数据，先执行 Java 项目的 `01_schema.sql`，再导入数据处理目录下的真实数据 SQL，最后执行 `03_trigger_procedure_view.sql`。

## 启动

先修改 `src/main/resources/application.yml` 中的数据库用户名和密码。

```bash
mvn spring-boot:run
```

也可以打包后启动：

```bash
mvn clean package -DskipTests
java -jar target-build/basketball-management-system-0.0.1-SNAPSHOT-boot.jar
```

本项目的 Maven 编译输出目录配置为 `target-build`，用于避开旧编译目录被占用的问题。Windows 中文路径下如果 `mvn spring-boot:run` 出现 classpath 编码问题，可以使用 IDE 启动 `com.yjt.basketball.BasketballApplication`。

## 页面访问路径

系统启动后先访问 `/login` 登录。课程演示账号如下：

| 角色 | 用户名 | 密码 | 权限说明 |
| --- | --- | --- | --- |
| 管理员 | `admin` | `123456` | 可查看全部页面，可删除比赛、更新比赛数据、管理球探报告 |
| 球探 | `scout` | `123456` | 可查看比赛和数据分析，可新增、编辑、删除球探报告 |
| 访客 | `viewer` | `123456` | 只读查看首页、球员档案、比赛管理和数据分析 |

登录用户表为 `UserAccount`。当前是课程演示用途，密码使用明文保存，方便初始化和截图说明；正式系统应改为 BCrypt 哈希存储。

| 页面 | 路径 | 用途 |
| --- | --- | --- |
| 首页 / 仪表盘 | `/` 或 `/dashboard` | 总览统计、快捷入口、最近比赛、Top 球员 |
| 球员档案 | `/players` | 球员查询、球队/位置/状态筛选 |
| 比赛管理 | `/games` | 赛程与比分管理、查看详情、事务删除比赛 |
| 比赛技术统计 | `/games/{gameId}` | 主客队球员技术统计、进入数据维护 |
| 球探报告 | `/scout-notes` | 查询、新增、编辑、删除球探笔记 |
| 数据分析 | `/stats-query` | 基于视图查询球员比赛表现 |
| 更新比赛数据 | `/stats-update` | 选择比赛后只维护本场球员数据 |

`/delete-game` 已保留为兼容路径，会跳转到 `/games`。

## 课程评分点对应关系

| 评分点 | 页面 | 接口 | 代码位置 |
| --- | --- | --- | --- |
| 含有事务应用的删除操作 | `/games` | `DELETE /api/games/{gameId}` | `GameService.deleteGameWithStats`，使用 `@Transactional` |
| 触发器控制下的添加操作 | `/scout-notes` | `POST /api/scout-notes` | `03_trigger_procedure_view.sql` 中 `trg_scoutnote_before_insert` |
| 存储过程控制下的更新操作 | `/stats-update` | `PUT /api/stats` | `StatsService.updateStats` 调用 `sp_update_player_game_stats` |
| 含有视图的查询操作 | `/stats-query` | `GET /api/stats/details` | `StatsService.findDetails` 查询 `v_player_game_detail` |

## 新增和优化的接口

- `GET /api/players?playerName=&teamId=&position=&status=`
- `GET /api/games?gameId=&teamName=&startDate=&endDate=`
- `GET /api/games/{gameId}/players`
- `GET /api/scout-notes?playerName=&minRating=&maxRating=&keyword=&page=&size=`
- `PUT /api/scout-notes/{noteId}`
- `DELETE /api/scout-notes/{noteId}`
- `GET /api/stats/current?gameId=&playerId=`

## 截图建议

- 首页：统计卡片、快捷入口、最近比赛、Top 球员。
- 比赛管理：顶部 `@Transactional` 说明、比赛列表、删除按钮。
- 球探报告：触发器说明、查询区、新增/编辑/删除按钮。
- 更新比赛数据：存储过程说明、四步流程、只显示本场球员的下拉框。
- 数据分析：视图说明、查询条件、视图查询结果数量。

详细接口测试样例见 `docs/api-test.md`。
