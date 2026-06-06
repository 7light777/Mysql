# API 测试说明

## 1. 数据库初始化

进入项目目录：

```bash
cd basketball-management-system
```

执行数据库脚本：

```bash
mysql -u root -p < src/main/resources/db/01_schema.sql
mysql -u root -p basketball_db < src/main/resources/db/02_demo_data.sql
mysql -u root -p basketball_db < src/main/resources/db/03_trigger_procedure_view.sql
```

脚本说明：

- `01_schema.sql`：创建 `basketball_db`，创建 Team、Player、ActivePlayer、RetiredPlayer、Game、PlayerGameStats、ScoutNote。
- `02_demo_data.sql`：插入球队、球员、比赛、比赛表现和球探笔记演示数据。
- `03_trigger_procedure_view.sql`：创建触发器、存储过程和视图。

## 2. 启动后端

修改 `src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    username: root
    password: 你的数据库密码
```

启动：

```bash
mvn spring-boot:run
```

验证：

```bash
curl http://localhost:8080/api/dashboard
```

浏览器页面验证：

- 首页：`http://localhost:8080/`
- 球探笔记触发器演示：`http://localhost:8080/scout-notes`
- 视图查询演示：`http://localhost:8080/stats-query`
- 存储过程更新演示：`http://localhost:8080/stats-update`
- 事务删除演示：`http://localhost:8080/delete-game`

## 3. 接口测试样例

### 3.1 首页统计接口

对应系统首页统计。

```bash
curl http://localhost:8080/api/dashboard
```

预期：返回球队数、球员数、比赛数、比赛技术统计记录数和场均得分最高球员。

### 3.2 视图查询接口

对应课程报告：含有视图的查询操作。

查询全部球员比赛表现：

```bash
curl "http://localhost:8080/api/stats/details"
```

按球员姓名模糊查询：

```bash
curl "http://localhost:8080/api/stats/details?playerName=LeBron"
```

按比赛 ID 查询：

```bash
curl "http://localhost:8080/api/stats/details?gameId=1"
```

组合查询：

```bash
curl "http://localhost:8080/api/stats/details?playerName=Curry&gameId=1"
```

### 3.3 触发器控制下的添加操作：合法数据

对应课程报告：触发器控制下的添加操作。

```bash
curl -X POST "http://localhost:8080/api/scout-notes" \
  -H "Content-Type: application/json" \
  -d "{\"playerId\":1,\"content\":\"投篮能力稳定，具备培养价值\",\"potentialRating\":8}"
```

预期：插入成功，返回新增球探笔记。

### 3.4 触发器控制下的添加操作：非法数据

`potentialRating=15` 超出触发器允许范围。

```bash
curl -X POST "http://localhost:8080/api/scout-notes" \
  -H "Content-Type: application/json" \
  -d "{\"playerId\":1,\"content\":\"潜力评分非法测试\",\"potentialRating\":15}"
```

预期：失败，返回类似：

```json
{
  "success": false,
  "message": "数据库完整性约束错误：potential_rating必须在1到10之间",
  "data": null
}
```

也可以测试空内容：

```bash
curl -X POST "http://localhost:8080/api/scout-notes" \
  -H "Content-Type: application/json" \
  -d "{\"playerId\":1,\"content\":\"\",\"potentialRating\":8}"
```

### 3.5 存储过程控制下的更新操作：合法数据

对应课程报告：存储过程控制下的更新操作。

```bash
curl -X PUT "http://localhost:8080/api/stats" \
  -H "Content-Type: application/json" \
  -d "{\"playerId\":1,\"gameId\":1,\"points\":28,\"rebounds\":8,\"assists\":6,\"steals\":2,\"blocks\":1}"
```

预期：调用 `sp_update_player_game_stats` 成功。存储过程会根据球员新旧得分差值自动同步更新 `Game` 中对应球队比分，并更新 `PlayerGameStats` 球员技术统计。

更新后可用视图查询验证：

```bash
curl "http://localhost:8080/api/stats/details?playerName=LeBron&gameId=1"
```

### 3.6 存储过程控制下的更新操作：非法数据

`points=-5` 由存储过程拦截。

```bash
curl -X PUT "http://localhost:8080/api/stats" \
  -H "Content-Type: application/json" \
  -d "{\"playerId\":1,\"gameId\":1,\"points\":-5,\"rebounds\":8,\"assists\":6,\"steals\":2,\"blocks\":1}"
```

预期：失败，返回类似：

```json
{
  "success": false,
  "message": "技术统计数据不能为负数",
  "data": null
}
```

### 3.7 事务删除某场比赛

对应课程报告：含有事务应用的删除操作。

建议使用 `gameId=4` 做演示。

删除前查询：

```bash
curl "http://localhost:8080/api/stats/details?gameId=4"
```

执行删除：

```bash
curl -X DELETE "http://localhost:8080/api/games/4"
```

预期：先删除 `PlayerGameStats` 中 `game_id=4` 的记录，再删除 `Game` 中 `game_id=4` 的比赛。

返回示例：

```json
{
  "success": true,
  "message": "比赛及比赛表现记录删除成功",
  "data": {
    "gameId": 4,
    "deletedStatsCount": 3
  }
}
```

删除后再次查询：

```bash
curl "http://localhost:8080/api/stats/details?gameId=4"
```

预期：返回空数组。

## 4. 接口与课程报告章节对应关系

| 接口 | 数据库对象/代码点 | 报告章节 |
| --- | --- | --- |
| `DELETE /api/games/{gameId}` | MySQL 事务过程 `sp_delete_game_with_stats_transaction` | 含有事务应用的删除操作 |
| `POST /api/scout-notes` | `trg_scoutnote_before_insert` | 触发器控制下的添加操作 |
| `PUT /api/stats` | `sp_update_player_game_stats` | 存储过程控制下的更新操作 |
| `GET /api/stats/details` | `v_player_game_detail` | 含有视图的查询操作 |

## 5. 建议截图位置

- 数据库连接串：`src/main/resources/application.yml`
- 事务删除代码：`src/main/java/com/yjt/basketball/service/GameService.java`
- 触发器 SQL：`src/main/resources/db/03_trigger_procedure_view.sql`
- 存储过程 SQL：`src/main/resources/db/03_trigger_procedure_view.sql`
- 视图 SQL：`src/main/resources/db/03_trigger_procedure_view.sql`
- 存储过程调用代码：`src/main/java/com/yjt/basketball/service/StatsService.java`
- 视图查询代码：`src/main/java/com/yjt/basketball/service/StatsService.java`
