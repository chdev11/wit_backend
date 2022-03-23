package org.example.shared.entities;

import java.math.BigDecimal;

public class AmqpResponse {
    public final BigDecimal result;

    public AmqpResponse(BigDecimal result) {
        this.result = result;
    }
}
