package core;

import java.time.LocalDateTime;

public class Transaction {
    private String description;
    private Double amount;
    private LocalDateTime time;

    public Transaction(String description, Double amount) {
        this.description = description;
        this.amount = amount;
        this.time = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
