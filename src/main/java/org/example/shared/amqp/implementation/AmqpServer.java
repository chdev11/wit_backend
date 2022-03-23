package org.example.shared.amqp.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.rest.entities.MathAction;
import org.example.shared.amqp.interfaces.IAmqpServer;
import org.example.shared.entities.AmqpResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
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
    public AmqpResponse sendMessage(MathAction data) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return (AmqpResponse) rabbitTemplate.convertSendAndReceive(queueName, gson.toJson(data));
    }
}
