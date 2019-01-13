package com.n26.transaction.web;

public class TransactionDTO {

    private String amount;
    private String timestamp;

    public TransactionDTO(String amount, String timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public static class TransactionDTOBuilder {

        private String amount;
        private String timestamp;

        public TransactionDTOBuilder withAmount(String amount) {
            this.amount = amount;
            return this;
        }

        public TransactionDTOBuilder withTimestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public TransactionDTO build() {
            return new TransactionDTO(amount, timestamp);
        }
    }
}
