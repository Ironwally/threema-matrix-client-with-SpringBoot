# Matrix via Spring Boot

- DemoApplication via docker does not work right now.
- Use normal spring boot execution so that it works: `.\gradlew.bat build` 
- skip tests: `.\gradlew.bat build -x test`
- Problem: matrix_communication_client sdk does not support E2EE

## Prerequesits
- Download Threema Java API: https://gateway.threema.ch/en/developer/sdk-java
  - Do initial setup via README.md commands executed inside libs/threema-msgapi-sdk-.../.../source/
- Download Matrix-Rust-SDK: 

## Fresh build & start
- run with tests
```
docker compose build --no-cache
docker compose up -d
```

- run without tests
```
docker compose build --build-arg SKIP_TESTS=true --no-cache
docker compose up -d
```

## Verify
```
docker compose ps
docker logs demo --tail 50
docker logs matrix-server --tail 50
```
## Stop
```
docker compose down
```

## Clean files
- Normal clean up
```
docker compose down --remove-orphans
rm ./synapse-data/homeserver.yaml
rm ./synapse-data/matrix.local.log.config
rm ./synapse-data/matrix.local.signing.key
```
Checklist:
- Stop and remove running containers
- Remove project images (especially demo:latest)
- Remove named volumes (matrix_db, matrix_turn) and any dangling volumes
- Remove builder cache layers (optional)
- Delete local generated folders that Docker rebuilds anyway (optional)
- Rebuild and start via docker compose
- Verify containers healthy
- Commands (PowerShell). Run inside the project root.

1. Stop and remove stack
```
docker compose down --remove-orphans
```

2. Remove named volumes (WARNING: wipes Matrix DB data)
```
docker volume rm matrix_db
docker volume rm matrix_turn
```

3. Remove the application image (forces full rebuild)
```
docker rmi demo:latest 2>$null
```
(If the `image name` differs after build, list images first: `docker images | Select-String demo`.)


- One-liner (aggressive full reset including volumes & caches):
```
docker compose down --remove-orphans; docker volume rm matrix_db matrix_turn 2>$null; docker rmi demo:latest 2>$null; docker builder prune -f; Remove-Item -Recurse -Force .\build,.\bin 2>$null; docker compose build --no-cache; docker compose up -d
```