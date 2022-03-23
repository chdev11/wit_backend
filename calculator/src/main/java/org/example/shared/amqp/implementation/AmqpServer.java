package org.example.shared.amqp.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.services.CalculatorService;
import org.example.shared.amqp.interfaces.IAmqpServer;
import org.example.shared.entities.AmqpResponse;
import org.example.shared.entities.MathAction;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class AmqpServer implements IAmqpServer {

    private static final Logger log = LogManager.getLogger(AmqpServer.class);

    @Autowired
    private CalculatorService calculatorService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${queue.name}")
    private String queueName;

    @Override
    @RabbitListener(queues = {"${queue.name}"})
    public String listen(@Payload String data) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        MathAction action = gson.fromJson(data, MathAction.class);
        MDC.put("request-id", action.transactionId);
        AmqpResponse response = calculatorService.call(action);

        log.info("[" + action.type.name() + "]: Transaction ID: " + MDC.get("request-id") + ". Result: " + response.result);

        return gson.toJson(response);
    }

    @Override
    public AmqpResponse sendMessage(MathAction data) throws Exception {
        throw new Exception("Not handled at this module");
    }
}
