package com.hka.intranet.messageprotocol.matrix.springboot.matrix_communication_client_sdk.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cosium.matrix_communication_client.CreateRoomInput;
import com.cosium.matrix_communication_client.MatrixResources;
import com.cosium.matrix_communication_client.Message;
import com.cosium.matrix_communication_client.RoomResource;

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
    @DisplayName("Matrix connectivity and message send")
    void matrixConnectionAndSendMessage() {
        String host = env("MATRIX_HOST", "matrix.local");
        String user = env("MATRIX_USERNAME", "admin");
        String pass = env("MATRIX_PASSWORD", "magentaerenfarve");
        String roomId = env("MATRIX_ROOM_ID", "!xhQeCglCmKHSGRksIp:matrix.local");

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
        }, "Failed to connect to Matrix homeserver or send message. Check server availability, credentials, and **room id**.");
    }

    @Test
    @DisplayName("Matrix create room")
    void matrixCreateRoom() {
        String host = env("MATRIX_HOST", "matrix.local");
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

            CreateRoomInput createRoomInput = CreateRoomInput.builder()
                .name("Test Room"+System.currentTimeMillis())
                .topic("This is a test room")
                .roomAliasName("test_room_"+System.currentTimeMillis())
                .preset("private_chat")
                .build();

            RoomResource room = matrix.rooms().create(createRoomInput);

            //set env roomID to room.id
            System.setProperty("MATRIX_CREATED_ROOM_ID", room.id());

            room.sendMessage(
                Message.builder()
                    .body("Room Creation test message")
                    .formattedBody("<b>Room Creation test message</b>")
                    .build()
            );
        }, "Failed to create room.");
    }

    @Test
    @DisplayName("Unsupported Matrix delete room. SDK does not support delete.")
    void unsupportedMatrixDeleteRoom() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            throw new UnsupportedOperationException("Matrix room deletion is not supported by the SDK.");
        });
    }
    @Test
    @DisplayName("Unsupported Matrix leave room. SDK does not support leave.")
    void unsupportedMatrixLeaveRoom() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            throw new UnsupportedOperationException("Matrix room leaving is not supported by the SDK.");
        });
    }
}
