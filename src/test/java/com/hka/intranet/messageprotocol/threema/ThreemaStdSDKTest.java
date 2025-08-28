package com.hka.intranet.messageprotocol.threema;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ch.threema.apitool.APIConnector;
import ch.threema.apitool.PublicKeyStore;
import ch.threema.apitool.helpers.E2EHelper;
import ch.threema.apitool.types.GroupId;
import ch.threema.apitool.utils.ApiResponse;
import io.github.cdimascio.dotenv.Dotenv;

class ThreemaConnectivityTest {

    // Load .env once (ignored if file missing). Enables local overrides without exporting env vars.
    private static final Dotenv DOTENV = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    private String env(String key, String def) {
        String v = System.getenv(key);
        if (v == null || v.isBlank()) {
            v = DOTENV.get(key);
        }
        return (v == null || v.isBlank()) ? def : v;
    }

    private APIConnector createAPIConnector(String gatewayId, String secret) {
        APIConnector connector = new APIConnector(gatewayId, secret, new PublicKeyStore() {
                private final Path keyDirectory = Paths.get("build", "threema-public-keys");

                @Override
                protected byte[] fetchPublicKey(String threemaId) {
                    // Attempt to load a cached public key from the local filesystem.
                    // File naming convention: <THREEMA_ID>.pub stored under build/threema-public-keys/
                    try {
                        Path keyFile = keyDirectory.resolve(threemaId + ".pub");
                        if (Files.exists(keyFile)) {
                            return Files.readAllBytes(keyFile);
                        }
                    } catch (IOException e) {
                        // Wrap in unchecked to propagate failure to test context
                        throw new RuntimeException("Failed to read public key for " + threemaId, e);
                    }
                    return null; // triggers remote fetch
                }

                @Override
                protected void save(String threemaId, byte[] publicKey) {
                    try {
                        if (!Files.exists(keyDirectory)) {
                            Files.createDirectories(keyDirectory);
                        }
                        Path keyFile = keyDirectory.resolve(threemaId + ".pub");
                        Files.write(keyFile, publicKey);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to save public key for " + threemaId, e);
                    }
                }
            });

        Assertions.assertNotNull(connector, "Connector should be created");
        return connector;
    }

    @Test
    @DisplayName("Threema connectivity and message send")
    void threemaConnection() {
        String gatewayId = env("threema.gatewayId", "*MYGWID1");
        String secret    = env("threema.secret", "SECRET_OF_MY_GATEWAY_ID");

        APIConnector connector = new APIConnector(gatewayId, secret, new PublicKeyStore() {
                private final Path keyDirectory = Paths.get("build", "threema-public-keys");

                @Override
                protected byte[] fetchPublicKey(String threemaId) {
                    // Attempt to load a cached public key from the local filesystem.
                    // File naming convention: <THREEMA_ID>.pub stored under build/threema-public-keys/
                    try {
                        Path keyFile = keyDirectory.resolve(threemaId + ".pub");
                        if (Files.exists(keyFile)) {
                            return Files.readAllBytes(keyFile);
                        }
                    } catch (IOException e) {
                        // Wrap in unchecked to propagate failure to test context
                        throw new RuntimeException("Failed to read public key for " + threemaId, e);
                    }
                    return null; // triggers remote fetch
                }

                @Override
                protected void save(String threemaId, byte[] publicKey) {
                    try {
                        if (!Files.exists(keyDirectory)) {
                            Files.createDirectories(keyDirectory);
                        }
                        Path keyFile = keyDirectory.resolve(threemaId + ".pub");
                        Files.write(keyFile, publicKey);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to save public key for " + threemaId, e);
                    }
                }
            });

    Assertions.assertNotNull(connector, "Connector should be created");
    // Basic interaction: ensure we can lookup email without exceptions (sanity check of instance usability)
    String testEmail = "JUnit-Test-UA@example.com";
    Assertions.assertDoesNotThrow(() -> connector.lookupEmail(testEmail),"Failed to create connection.");
    }

    @Test
    @DisplayName("Threema connectivity and message send")
    void threemaMessageSend() {

        String gatewayId = env("threema.gatewayId", "*MYGWID1");
        String secret    = env("threema.secret", "SECRET_OF_MY_GATEWAY_ID");

        APIConnector connector = createAPIConnector(gatewayId, secret);

        String text = "Hello, Threema!";
        String toThreemaId = env("threema.to", "ECHOECHO");
        ApiResponse<String> response = Assertions.assertDoesNotThrow(
            () -> connector.sendTextMessageSimple(toThreemaId, text),
            "sendTextMessageSimple threw an exception"
        );
        String messageId = response.getData();
        
        System.out.println("Message ID: " + messageId);
        Assertions.assertNotNull(messageId, "Message ID should not be null");
        Assertions.assertFalse(messageId.isBlank(), "Message ID should not be blank");
    }

    @Test
    @DisplayName("Threema Group message send")
    void threemaGroupMessageSend() {

        String gatewayId = env("threema.gatewayId", "*MYGWID1");
        String secret    = env("threema.secret", "SECRET_OF_MY_GATEWAY_ID");

        APIConnector connector = createAPIConnector(gatewayId, secret);
        E2EHelper e2eHelper = new E2EHelper(connector,null);

        String text = "Hello, Threema Group!";
        List<String> threemaIds = Arrays.asList(env("threema.id1", "ID1"), env("threema.id2", "ID2"));
        GroupId toGroupId = new GroupId(env("threema.group.to", "GROUP_ID"));

        ApiResponse<JSONArray> response = Assertions.assertDoesNotThrow(() ->

            e2eHelper.sendGroupTextMessage(
                threemaIds,
                toGroupId,
                text
            )
        );

        JSONArray messageArray = response.getData();

        System.out.println("Message Array: " + messageArray);
        Assertions.assertNotNull(messageArray, "Message Array should not be null");
        Assertions.assertFalse(messageArray.isEmpty(), "Message Array should not be empty");
    }
}