package com.n26.transaction;

import java.math.BigDecimal;
import java.time.Instant;

public class TransactionDO {

    private final BigDecimal amount;
    private final Instant timestamp;

    public TransactionDO(BigDecimal amount, Instant timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
