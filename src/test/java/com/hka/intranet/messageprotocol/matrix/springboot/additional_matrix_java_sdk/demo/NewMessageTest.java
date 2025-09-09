package com.hka.intranet.messageprotocol.matrix.springboot.additional_matrix_java_sdk.demo;

import org.junit.jupiter.api.Test;

import com.cosium.matrix_communication_client.CreateRoomInput;
import com.cosium.matrix_communication_client.MatrixResources;
import com.cosium.matrix_communication_client.RoomResource;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

// Own API
import com.hka.intranet.messageprotocol.matrix.springboot.additional_matrix_java_sdk.module_instant_Messaging.room.messages.MessageBase;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NewMessageTest {

    @Test
    @Order(3)
    void testNewMessageCreation() {
        // Arrange
        String content = "Hello, Matrix!";
        String sender = "user123";
        String roomId = System.getProperty("MATRIX_CREATED_ROOM_ID");
        RoomResource room = matrix.rooms().byId(roomId);

        // Act
        Assertions.assertDoesNotThrow(() -> {

            // Create messages
            MessageBase messageText = MessageBase.builder()
                    .text(content) // already give format here?
                    .build();
            MessageBase messageImage = MessageBase.builder()
                    .image("image_url.jpg", "An image")
                    .build();
            MessageBase messageFile = MessageBase.builder()
                    .file("document.pdf", "A PDF document")
                    .build();
            MessageBase messageAudio = MessageBase.builder()
                    .audio("audio.mp3", "An audio file")
                    .build();
            MessageBase messageSticker = MessageBase.builder()
                    .sticker("sticker_id_456")
                    .build();
            MessageBase messagePoll = MessageBase.builder()
                    .hidden()
                    .poll("Revealed after manual ending", new String[]{"Red", "Blue", "Green"})
                    .build();
            MessageBase messagePoll = MessageBase.builder()
                    .open()
                    .poll("Revealed after vote", new String[]{"Red", "Blue", "Green"})
                    .build();

            // Modify messages after sending
            MessageBase messagePollEnd = MessageBase.builder()
                    .fromMessageId("poll_message_id_789")
                    .pollEnd()
                    .build();
            MessageBase messageTextModify = MessageBase.builder()
                    .fromMessageId("text_message_id_123")
                    .text("Modified text")
                    .build();

            // Send messages
            room.sendMessage(
                messageText    
            );
            room.sendMessage(
                messageImage    
            );


            }, "Failed to create or send messages");        

        // Assert
        assertNotNull(message);
        assertEquals(sender, message.getSender());
        assertEquals(content, message.getContent());
    }
}