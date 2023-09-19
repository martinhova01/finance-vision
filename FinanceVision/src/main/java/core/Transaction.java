package core;

import java.time.LocalDateTime;

public abstract class Transaction {
    private String description;
    private Double amount;
    private String category;
    private LocalDateTime time;

    public Transaction(String description, Double amount, String category) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.time = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public Double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
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

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTime(LocalDateTime time){
        this.time = time;
    }

}
