package com.hka.intranet.messageprotocol.matrix.springboot.matrix_communication_client_sdk.demo;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.cosium.matrix_communication_client.CreateRoomInput;
import com.cosium.matrix_communication_client.MatrixResources;
import com.cosium.matrix_communication_client.RoomResource;
import com.cosium.matrix_communication_client.message.Message;

import io.github.cdimascio.dotenv.Dotenv;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NewMessageTest {
        
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

    // Shared across tests (created in test with @Order(1))
    private static MatrixResources MATRIX;
    private static RoomResource ROOM;

    @Test
    @Order(2)
    void testNewMessageCreation() {
        // Arrange
        assertNotNull(ROOM, "ROOM not initialized. Ensure matrixCreateRoom ran first.");
        assertNotNull(MATRIX, "MATRIX not initialized.");
        String content = "Hello, Matrix!";
        RoomResource room = ROOM;
        MatrixResources matrix = MATRIX;
    
        // Act
        Assertions.assertDoesNotThrow(() -> {
            // Create messages
            Message messageText = Message.builder()
                    .text(content)
                    .build();
            String mxc_uri = matrix.uploadFile("An_image.png");
            Message messageImage = Message.builder()
                    .image("An image", mxc_uri) // fetch here already?
                    .fillMetaData() // fetches image metadata from server
                    .build();
            String mxc_uri2 = matrix.uploadFile("document.pdf");
            Message messageFile = Message.builder()
                    .file("A PDF document", mxc_uri2) // fetch here already?
                    .build();
            Message messageAudio = Message.builder()
                    .audio("An audio file", "audio.mp3")
                    .build();
            Message messageSticker = Message.builder()
                    .sticker("sticker_id_456")
                    .build();
            Message messagePollClosed = Message.builder()
                    .poll("Revealed after manual ending", new String[]{"Red", "Blue", "Green"})
                    .hidden()
                    .build();
            Message messagePollOpen = Message.builder()
                    .poll("Revealed after vote", new String[]{"Red", "Blue", "Green"})
                    .open()
                    .build();

            // Modify messages after sending
            // TODO: Use fetchEventPage to get message IDs?
            Message messagePollEnd = Message.builder()
                    .fromMessageId("poll_message_id_789")
                    .pollEnd()
                    .build();
            Message messageTextModify = Message.builder()
                    .fromMessageId("text_message_id_123")
                    .text("Modified text")
                    .build();


            // TODO: New approach to modifying library: Fix problem in source code and package as with same Maven coordinates and add classifier
            // https://stackoverflow.com/questions/34121725/modifying-files-from-external-library-in-java 
            
            // -> Migrate current changes to fork of sdk and implement them there
            
            // Send messages
            // TODO: Extend functionality by implementing own class based on library:
            // TODO: Implement sendMessage for different Message types
            // TODO: Implement modify for messages
            // https://stackoverflow.com/questions/36952056/extend-a-java-library-by-adding-methods-to-it
            // https://spec.matrix.org/latest/client-server-api/#mroommessage
            room.sendMessage(
                messageText    
            );
            room.sendMessage(
                messageImage    
            );
            }, "Failed to create or send messages");        

        // Assert
    // basic sanity
    assertNotNull(ROOM);
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
            ROOM = room; // share with other tests

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
}