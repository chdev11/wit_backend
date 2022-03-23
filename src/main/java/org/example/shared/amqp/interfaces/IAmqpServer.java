package org.example.shared.amqp.interfaces;

import org.springframework.messaging.handler.annotation.Payload;

public interface IAmqpServer {
    Object listen(@Payload String data) throws Exception;
    void sendMessage(String message) throws Exception;
}