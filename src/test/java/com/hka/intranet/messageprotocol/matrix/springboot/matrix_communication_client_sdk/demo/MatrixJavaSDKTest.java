package com.hka.intranet.messageprotocol.matrix.springboot.matrix_communication_client_sdk.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.cosium.matrix_communication_client.ClientEventPage;
import com.cosium.matrix_communication_client.CreateRoomInput;
import com.cosium.matrix_communication_client.MatrixResources;
import com.cosium.matrix_communication_client.RoomResource;
import com.cosium.matrix_communication_client.message.Message;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Integration style test that attempts to build a Matrix client and send a message
 * to a configured room. It will fail if any exception occurs (e.g., homeserver not reachable,
 * authentication failure, unknown room, etc.).
 *
 * NOTE: This requires a reachable Matrix homeserver and a valid room id. Adjust
 * environment variables if needed:
 *   MATRIX_HOST (default: localhost)
 *   MATRIX_USERNAME (default: admin)
 *   MATRIX_PASSWORD (default: magentaerenfarve)
 *   MATRIX_ROOM_ID (default: !GhYHbLfNOTzPklsVQY:matrix.local)
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MatrixJavaSDKTest {

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

    private static MatrixResources MATRIX;
    private static RoomResource ROOM;

    @Test
    @DisplayName("Matrix message send")
    @Order(2)
    void matrixSendMessage() {
        RoomResource room = ROOM;

        Assertions.assertDoesNotThrow(() -> {
            room.sendMessage(
                Message.builder()
                    .text("MessageSend test message")
                    .build()
            );
        }, "Failed to connect to send message. Check if test 'Matrix create room' succeeded.");
    }

    @Test
    @DisplayName("Matrix create room")
    @Order(1)
    void matrixCreateRoom() {
        String host = env("MATRIX_HOST", "localhost");
        String user = env("MATRIX_USERNAME", "admin");
        String pass = env("MATRIX_PASSWORD", "magentaerenfarve");
        
        Assertions.assertDoesNotThrow(() -> {
            MatrixResources matrix = MatrixResources.factory()
                .builder()
                .http()
                .hostname(host)
                .defaultPort()
                .usernamePassword(user, pass)
                .build();
            MATRIX = matrix; // share with other tests

            CreateRoomInput createRoomInput = CreateRoomInput.builder()
                .name("Test Room"+System.currentTimeMillis())
                .topic("This is a test room")
                .roomAliasName("test_room_"+System.currentTimeMillis())
                .preset("private_chat")
                .build();

            RoomResource room = matrix.rooms().create(createRoomInput);

            Assertions.assertNotNull(room);
            Assertions.assertNotNull(room.id());

            ROOM = room; // share with other tests

            room.sendMessage(
                Message.builder()
                    .text("Room Creation test message")
                    .build()
            );
        }, "Failed to create room.");
    }

    @Test
    @DisplayName("matrixFetchPage. Test not done yet")
    void matrixFetchPage() {
        RoomResource room = ROOM;

        String dir = "b"; // direction of returning events by: "b" for backwards, "f" for forwards
        long limit = 1; // Number of events to return
        String from = null; // Token to start returning events from. Aquired from sync endpoint
        String to = null; // Token to stop returning events at. Aquired from sync endpoint
        ClientEventPage eventPage = room.fetchEventPage(dir, from, limit, to);
        
        Assertions.assertNotNull(eventPage);
    }

    /*

    @Test
    @DisplayName("Unsupported Matrix delete room. SDK does not support delete.")
    void unsupportedMatrixDeleteRoom() {
    }

    @Test
    @DisplayName("Unsupported Matrix leave room. SDK does not support leave.")
    void unsupportedMatrixLeaveRoom() {
    }
    */
}
