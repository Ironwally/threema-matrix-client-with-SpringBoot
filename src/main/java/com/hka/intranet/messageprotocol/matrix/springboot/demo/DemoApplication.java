package com.hka.intranet.messageprotocol.matrix.springboot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cosium.matrix_communication_client.*;
import java.util.logging.Logger;

@SpringBootApplication
public class DemoApplication {
    Logger logger = Logger.getLogger(getClass().getName());

    public void matrixSendTextMessage() {
          logger.info("Running matrixSendTextMessage");  // Compliant, output via logger

		MatrixResources matrix =
        MatrixResources.factory()
            .builder()
            .http()
            .hostname("matrix.local")
            .defaultPort()
            .usernamePassword("admin", "magentaerenfarve")
            .build();
    	RoomResource room = matrix
        .rooms()
        .byId("!GhYHbLfNOTzPklsVQY:matrix.local");

    room.sendMessage(Message.builder().body("DemoApplication-Main Connectivity Test Message !").formattedBody("<b>DemoApplication Main Connectivity Test Message !</b>").build());
	}    

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
        new DemoApplication().matrixSendTextMessage();
	}

}
