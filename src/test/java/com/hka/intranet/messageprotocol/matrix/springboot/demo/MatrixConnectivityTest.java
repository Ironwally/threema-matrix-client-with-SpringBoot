package com.hka.intranet.messageprotocol.matrix.springboot.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;

import com.cosium.matrix_communication_client.*;

/**
 * Integration style test that attempts to build a Matrix client and send a message
 * to a configured room. It will fail if any exception occurs (e.g., homeserver not reachable,
 * authentication failure, unknown room, etc.).
 *
 * NOTE: This requires a reachable Matrix homeserver and a valid room id. Adjust
 * environment variables if needed:
 *   MATRIX_HOST (default: matrix.local)
 *   MATRIX_USERNAME (default: admin)
 *   MATRIX_PASSWORD (default: magentaerenfarve)
 *   MATRIX_ROOM_ID (default: !GhYHbLfNOTzPklsVQY:matrix.local)
 */
class MatrixConnectivityTest {

    private String env(String key, String def) {
        String v = System.getenv(key);
        return (v == null || v.isBlank()) ? def : v;
    }

    @Test
    @DisplayName("Matrix homeserver connectivity and message send")
    void matrixConnectionAndSendMessage() {
        String host = env("MATRIX_HOST", "matrix.local");
        String user = env("MATRIX_USERNAME", "admin");
        String pass = env("MATRIX_PASSWORD", "magentaerenfarve");
        String roomId = env("MATRIX_ROOM_ID", "!GhYHbLfNOTzPklsVQY:matrix.local");

        Assertions.assertDoesNotThrow(() -> {
            MatrixResources matrix = MatrixResources.factory()
                .builder()
                .http()
                .hostname(host)
                .defaultPort()
                .usernamePassword(user, pass)
                .build();

            RoomResource room = matrix.rooms().byId(roomId);
            room.sendMessage(
                Message.builder()
                    .body("Connectivity test message")
                    .formattedBody("<b>Connectivity test message</b>")
                    .build()
            );
        }, "Failed to connect to Matrix homeserver or send message. Check server availability, credentials, and room id.");
    }
}
