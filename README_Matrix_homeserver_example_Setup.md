# Matrix homeserver example

This is an example on how to host a local matrix homeserver, for development, using the synapse docker image + a reverse-proxy using nginx.

## Generate homeserver config

The example does not include the synapse-config, but includes a script which runs nessaery commands to generate the config:

```bash
chmod +x generate_synapse_config.sh && ./generate_synapse_config.sh
```

This command wil generate the config files for the homeserver inside the empty `./synapse-data`-folder. 

## Start the homeserver

```
docker compose up -d
```

## Create Admin user

The homeserver will start wihtout any users. To create an initial admin user, the following command be used as example:

```bash
docker exec matrix-server register_new_matrix_user -u admin -p magentaerenfarve --admin -c /data/homeserver.yaml http://matrix.local:8008
```

## Matrix client app - connect to the server

Last thing is to actually use the Matrix homeserver, which you can do by using existing clients like [Element.io](https://element.io/)
or [Cinny](https://cinny.in/).. or by creating your own client 🙃

### Using Element.io

After downloading the app, open it and go to "Sign in". By default, element want to use `matrix.org` as the OIDC provider, but we want to use our own homeserver..

Click the "edit"-button and choose the "other homeserver"-option. In this field, enter `http://matrix.local` and press OK.
If everything was done correctly, this will send you back to the sign in form, where you are now able to sign in using your newly created admin account.