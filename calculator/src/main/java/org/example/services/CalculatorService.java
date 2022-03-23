package org.example.services;

import org.example.shared.entities.AmqpResponse;
import org.example.shared.entities.MathAction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CalculatorService {

    public AmqpResponse call(MathAction action) {
        switch (action.type) {
            case SUM:
                return new AmqpResponse(sum(action.a, action.b));
            case SUB:
                return new AmqpResponse(sub(action.a, action.b));
            case DIV:
                return new AmqpResponse(div(action.a, action.b));
            case MUL:
                return new AmqpResponse(mul(action.a, action.b));
        }
        return null;
    }

    private BigDecimal sum(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    private BigDecimal sub(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }

    private BigDecimal div(BigDecimal a, BigDecimal b) {
        return a.divide(b, 1);
    }

    private BigDecimal mul(BigDecimal a, BigDecimal b) {
        return a.multiply(b);
    }
}
