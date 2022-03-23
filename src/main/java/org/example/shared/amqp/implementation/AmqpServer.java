package org.example.shared.amqp.implementation;

import org.example.shared.amqp.interfaces.IAmqpServer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;

public class AmqpServer implements IAmqpServer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${queue.name}")
    private String queueName;

    @Override
    public Object listen(String data) throws Exception {
        throw new Exception("Not handled at this module");
    }

    @Override
    public void sendMessage(String data) {
        MessageProperties messageProperties = new MessageProperties();
        //TODO: create UUID generator
        messageProperties.setHeader("request-id", "");
        Message message = new Message(data.getBytes(StandardCharsets.UTF_8), messageProperties);
        System.out.println("response: " + rabbitTemplate.convertSendAndReceive(queueName, message));
    }
}
