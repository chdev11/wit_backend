package org.example.shared.entities;

import org.example.shared.enums.MathType;

import java.math.BigDecimal;

public class MathAction {
    public String transactionId;
    public final MathType type;
    public final BigDecimal a;
    public final BigDecimal b;

    public MathAction(MathType type, BigDecimal a, BigDecimal b) {
        this.type = type;
        this.a = a;
        this.b = b;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(String uuid) {
        this.transactionId = uuid;
    }
}
