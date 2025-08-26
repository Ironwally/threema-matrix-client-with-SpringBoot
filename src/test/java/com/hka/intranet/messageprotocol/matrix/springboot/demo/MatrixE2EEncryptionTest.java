package com.hka.intranet.messageprotocol.matrix.springboot.demo;

import com.cosium.matrix_communication_client.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.Assertions;

/**
 * Attempts to send a (NON-E2E) plaintext message to a room that is configured for
 * end-to-end encryption. The currently used SDK (matrix-communication-client 1.9)
 * does not expose Olm/Megolm crypto primitives nor any API surface for producing
 * m.room.encrypted events, so true client-side encryption is NOT performed here.
 *
 * Purpose: Verifies that the application can still reach the homeserver and
 * interact with the encrypted room (server typically does not reject a
 * plaintext event, but other clients will flag it as unencrypted). This test
 * will need replacing once an SDK with E2E support (or a crypto proxy such as
 * Pantalaimon) is integrated.
 *
 * Activation: enabled only when the JVM system property 'e2eTests' is set to 'true'.
 * Example: ./gradlew test -De2eTests=true
 * Optional env overrides:
 *   MATRIX_HOST (default: matrix.local)
 *   MATRIX_USERNAME (default: admin)
 *   MATRIX_PASSWORD (default: magentaerenfarve)
 *   MATRIX_E2E_ROOM_ID (default: !TGjoCeAaQfARAPvZET:matrix.local)
 */
@EnabledIfSystemProperty(named = "e2eTests", matches = "true")
class MatrixE2EEncryptionTest {

    private String env(String key, String def) {
        String v = System.getenv(key);
        return (v == null || v.isBlank()) ? def : v;
    }

    @Test
    @DisplayName("Attempt plaintext send to E2E room (crypto NOT implemented)")
    void sendPlaintextToEncryptedRoom() {
        String host = env("MATRIX_HOST", "matrix.local");
        String user = env("MATRIX_USERNAME", "admin");
        String pass = env("MATRIX_PASSWORD", "magentaerenfarve");
        String roomId = env("MATRIX_E2E_ROOM_ID", "!TGjoCeAaQfARAPvZET:matrix.local");

        Assertions.assertDoesNotThrow(() -> {
            MatrixResources matrix = MatrixResources.factory()
                .builder()
                .http()
                .hostname(host)
                .defaultPort()
                .usernamePassword(user, pass)
                .build();

            RoomResource room = matrix.rooms().byId(roomId);
            ClientEventResource event = room.sendMessage(
                Message.builder()
                    .body("(WARN: plaintext) E2E test probe")
                    .formattedBody("<i>(WARN: plaintext) E2E test probe</i>")
                    .build()
            );
            Assertions.assertNotNull(event, "Homeserver returned null event resource");
        }, () -> "Failed sending even plaintext message to encrypted room '" + roomId + "'. Check connectivity/credentials.");
    }
}
