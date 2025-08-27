# Matrix via Spring Boot

- DemoApplication via docker does not work right now.
- Use normal spring boot execution so that it works: `.\gradlew.bat build` 
- skip tests: `.\gradlew.bat build -x test`
- Problem: matrix_communication_client sdk does not support E2EE

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