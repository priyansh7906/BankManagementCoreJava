package domain;

import java.time.LocalDateTime;

public class Transaction {
    private String accountNumber;
    private Double amount;
    private String transactionId;
    private String note;
    private LocalDateTime dateTime;
    private Type type;

    // ✅ Constructor (must match the order of arguments)
    public Transaction(String accountNumber, Double amount, String transactionId,
                       String note, LocalDateTime dateTime, Type type) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.transactionId = transactionId;
        this.note = note;
        this.dateTime = dateTime;
        this.type = type;
    }

    // ✅ Getters and Setters (optional but recommended)
    public String getAccountNumber() {
        return accountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getNote() {
        return note;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Type getType() {
        return type;
    }
}
