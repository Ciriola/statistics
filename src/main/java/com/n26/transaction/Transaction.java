package com.n26.transaction;

public class Transaction {

    private String amount;
    private String timestamp;

    public Transaction() {}

    public Transaction(String amount, String timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
        sb.append("amount='").append(amount).append('\'');
        sb.append(", timestamp='").append(timestamp).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
