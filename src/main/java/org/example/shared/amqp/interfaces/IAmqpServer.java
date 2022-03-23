package org.example.shared.amqp.interfaces;

import org.example.rest.entities.MathAction;
import org.example.shared.entities.AmqpResponse;
import org.springframework.messaging.handler.annotation.Payload;

public interface IAmqpServer {
    Object listen(@Payload String data) throws Exception;
    AmqpResponse sendMessage(MathAction message) throws Exception;
}