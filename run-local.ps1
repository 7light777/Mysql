param(
    [int]$Port = 8080
)

$ErrorActionPreference = "Stop"

Write-Host "Packaging application..."
mvn package -DskipTests

Write-Host "Starting Spring Boot jar on port $Port..."
java -jar ".\target\basketball-management-system-0.0.1-SNAPSHOT.jar" "--server.port=$Port"
