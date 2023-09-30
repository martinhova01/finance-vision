package core;

import java.time.LocalDateTime;

public abstract class Transaction {
    private String description;
    private double amount;
    private String category;
    private LocalDateTime time;

    public Transaction(String description, double amount, String category) {
        setDescription(description);
        setAmount(amount);
        setCategory(category);
        this.time = LocalDateTime.now();
    }


    public Transaction(String description, double amount, String category, LocalDateTime time) {
        this(description, amount, category);
        setTime(time);
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
        if (description.equals("")){
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        this.description = description;
    }

    public void setAmount(double amount) {
        if (amount < 0){
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.amount = amount;
    }

    public void setCategory(String category) {
        if (category.equals("")){
            throw new IllegalArgumentException("Category cannot be empty.");
        }
        this.category = category;
    }

    public void setTime(LocalDateTime time){
        if (time == null){
            throw new IllegalArgumentException("Time is not set");
        }
        this.time = time;
    }

}
