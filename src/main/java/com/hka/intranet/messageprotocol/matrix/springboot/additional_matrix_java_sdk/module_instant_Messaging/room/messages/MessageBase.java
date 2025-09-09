package com.hka.intranet.messageprotocol.matrix.springboot.additional_matrix_java_sdk.module_instant_Messaging.room.messages;

import com.cosium.matrix_communication_client.Message;

public class MessageBase extends Message {

    private long timestamp;
    private long id;

    private MessageBase(String body, String format, String formattedBody, String type) {
        Message.builder()
                    .body(body)
                    .formattedBody(formattedBody)
                    .format(format)
                    .type(type)
                    .build();
    }



}
