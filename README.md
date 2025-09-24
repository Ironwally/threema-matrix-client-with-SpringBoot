# Threema-Matrix via Spring Boot

- Spring Boot via docker does not work right now.
- Run via IDE. All logic is found in the tests
- Or via commands: `.\gradlew.bat build` 
- skip tests (only to check if build fails): `.\gradlew.bat build -x test`
- Problem to fix: matrix_communication_client sdk does not support E2EE
  - Integrate Matrix-Rust-SDK instead?

# Setup

## Prerequesits 
- removes IDE Error messages: Download Threema Java API: https://gateway.threema.ch/en/developer/sdk-java
  - Run initial setup commands in included README.md. 
  - Execute the commands inside libs/threema-msgapi-sdk-java-2.2.0/.../source/
- recommended: Download `Element` from `matrix.org`


# For Threema
- You need an active Gateway Licence to test and use the threema Gateway API. 
- But you can look at nice code and just imagine it works 
- Set that up
- Manually put gatewayId, secret into tests or create a .env file. But you'll probably need to fix stuff in the tests then. Aka.: `System.getProperty(..)..`  
- **Remeber: Without a valid `Gateway Licence` and its `gatewayId` and `secret` manually set in the tests, they will always fail**

# For Matrix 

## Usage
- First create a `.env` file in the main directory. Here you will put all configuration like: the *matrix-hostname* and *roomid*.
- To send a message to a specific room open element-desktop and get the room ID in the *room settings* under *advanced*. Then set `MATRIX_ROOM_ID=theMatrixRoomID` in the .env file. Now you can run the tests

## Local host name (matrix.local)
The standard server address for matrix is `localhost`

Options:
1. Use `localhost` (Matrix user IDs will still show `:matrix.local`).
  - Set `MATRIX_HOST=localhost` in the `.env` file
2. OR: Add a hosts entry so `matrix.local` resolves locally:
  - Set `MATRIX_HOST=matrix.local` in the `.env` file
  - Windows 
    - Open an elevated PowerShell (Run as Administrator) and run:
      ```powershell
      Add-Content -Path "$env:SystemRoot\System32\drivers\etc\hosts" -Value "`n127.0.0.1 matrix.local # For matrix server under local address"
      ```
  - Linux
      ```
      if ! grep -q "127.0.0.1 matrix.local" /etc/hosts; then
        echo "matrix.local missing in /etc/hosts, adding..."
        echo "127.0.0.1 matrix.local" | sudo tee -a /etc/hosts > /dev/null
        echo "matrix.local added to /etc/hosts"
      fi
      ```

## Startup
- you might need to use sudo for some commands
### Fresh build & start
```
docker compose build (--no-cache)
docker compose up -d
```
- run `build` with `--no-cache` if you want a completely fresh build

### Verify
```
docker compose ps
docker logs matrix-server --tail 50
```
- should be no error messages

### Create matrix admin account
```bash
docker exec matrix-server register_new_matrix_user -u admin -p magentaerenfarve --admin -c /data/homeserver.yaml http://matrix.local:8008
```

### See Results
- Login with credentials via a client: `https://app.element.io/` or `Element app`
  - homeserver: `http://matrix.local` or `http://localhost` (if you set that before)
    - if it doesn't work, try your address directly, should be: `http://127.0.0.1`
    - if it still doesn't work, consider downloading `Element` from `matrix.org` and trying with that
    - check if your local adress is set to point to `matrix.local` in your `/etc/hosts` or your equivalent on windows (should have been set by script)
  - credentials:
    - name: `admin`
    - password: `magentaerenfarve`
- After you ran the tests you should see the messages/room created in you client

## Shutdown
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

# Credits
- Credits to @thorastrup My docker compose Matrix and nginx is based his, Matrix admin creation command is also from him 