package org.example.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.rest.entities.MathAction;
import org.example.rest.enums.MathType;
import org.example.shared.amqp.implementation.AmqpServer;
import org.example.shared.entities.AmqpResponse;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

@RestController
public class RequestController {

    private static final Logger log = LogManager.getLogger(RequestController.class);

    @Autowired
    private AmqpServer server;

    public ResponseEntity<Object> createAction(MathAction action) {
        if(action.a == null || action.b == null) {
            String errorMessage = "Error on create a Math action. A or B value are null.";
            log.error(errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", errorMessage));
        }

        String uuid = UUID.randomUUID().toString();
        action.setTransactionId(uuid);
        MDC.put("request-id", uuid);

        log.info("[" + action.type.name() + "]: Transaction ID: " + MDC.get("request-id") + ". Operation: "+ action.a + " and " + action.b);
        AmqpResponse response = server.sendMessage(action);
        if(response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "Error to connect with Calculator service."));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("result", response.result));
    }

    @GetMapping("/sum")
    public ResponseEntity<Object> sum(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        MathAction action = new MathAction(MathType.SUM, a, b);
        return createAction(action);
    }

    @GetMapping("/sub")
    public ResponseEntity<Object> sub(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        MathAction action = new MathAction(MathType.SUB, a, b);
        return createAction(action);
    }

    @GetMapping("/div")
    public ResponseEntity<Object> div(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        MathAction action = new MathAction(MathType.DIV, a, b);
        return createAction(action);
    }

    @GetMapping("/mul")
    public ResponseEntity<Object> mul(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        MathAction action = new MathAction(MathType.MUL, a, b);
        return createAction(action);
    }
}
