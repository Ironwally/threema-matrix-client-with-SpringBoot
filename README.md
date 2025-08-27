# Threema-Matrix via Spring Boot

- DemoApplication via docker does not work right now.
- Use normal spring boot execution so that it works: `.\gradlew.bat build` 
- skip tests: `.\gradlew.bat build -x test`
- Problem: matrix_communication_client sdk does not support E2EE

# Setup

## Prerequesits
- Download Threema Java API: https://gateway.threema.ch/en/developer/sdk-java
  - Do initial setup via README.md commands executed inside libs/threema-msgapi-sdk-.../.../source/
- Download Matrix-Rust-SDK: 

# For Threema
- You need an active Gateway Licence to test and use the threema Gateway API. 
- Set that up 
- Manually put gatewayId, secret, etc. into tests or create a .env file. But you'll probably need to fix stuff in the tests then. Aka.: `System.getProperty(..)..`  

# For Matrix 
## Fresh build & start
```
docker compose build
docker compose up -d
```
- run build with `--no-cache` if you want a fresh build

## Verify
```
docker compose ps
docker logs matrix-server --tail 50
```

## Create matrix admin account
```bash
docker exec matrix-server register_new_matrix_user -u admin -p magentaerenfarve --admin -c /data/homeserver.yaml http://matrix.local:8008
```

## See Results
- Login with credentials via a client
- After you ran the tests you should see the messages/room created in you client

## Stop
```
docker compose down
```

## Clean files
```
docker compose down --remove-orphans
rm ./synapse-data/homeserver.yaml
rm ./synapse-data/matrix.local.log.config
rm ./synapse-data/matrix.local.signing.key
```
Full Cleanup Checklist:
- Stop and remove running containers
- Remove project images (especially demo:latest)
- Remove named volumes (matrix_db, matrix_turn) and any dangling volumes
- Remove builder cache layers (optional)
- Delete local generated folders that Docker rebuilds anyway (optional)
- Rebuild and start via docker compose
- Verify containers healthy
- Commands (PowerShell). Run inside the project root.

- One-liner (aggressive full reset including volumes & caches):
- **!Be careful if you have other docker containers!**
```
docker compose down --remove-orphans; docker volume rm matrix_db matrix_turn 2>$null; docker rmi demo:latest 2>$null; docker builder prune -f; Remove-Item -Recurse -Force .\build,.\bin 2>$null; docker compose build --no-cache; docker compose up -d
```

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


# Credits
- Credits to @thorastrup My docker compose Matrix and nginx is based his, Matrix admin creation command is also from him 