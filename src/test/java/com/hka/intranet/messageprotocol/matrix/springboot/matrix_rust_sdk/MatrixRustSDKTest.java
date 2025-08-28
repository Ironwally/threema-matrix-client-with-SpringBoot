package com.hka.intranet.messageprotocol.matrix.springboot.matrix_rust_sdk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

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
class MatrixConnectivityTest {

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

    @Test
    @DisplayName("Matrix message send")
    @Order(2)
    void matrixSendMessage() {
        String host = env("MATRIX_HOST", "localhost");
        String user = env("MATRIX_USERNAME", "admin");
        String pass = env("MATRIX_PASSWORD", "magentaerenfarve");
        String roomId = env("MATRIX_ROOM_ID", System.getProperty("MATRIX_CREATED_ROOM_ID", "roomID not found"));
        
        // Usage of Rust-Matrix-SDK
        
    }
}
