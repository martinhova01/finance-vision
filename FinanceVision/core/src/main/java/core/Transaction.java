package core;

import java.time.LocalDateTime;

/**
 * Abstract class that contains logic for a transaction.
 */
public abstract class Transaction {
    private String description;
    private double amount;
    private String category;
    private LocalDateTime time;

    /**
     * Creates a new transaction, using a given description, amount and category.
     *
     * @param description the given description
     * @param amount the given amount
     * @param category the given category
     */
    public Transaction(String description, double amount, String category) {
        setDescription(description);
        setAmount(amount);
        setCategory(category);
        this.time = LocalDateTime.now();
    }

    /**
     * Creates a new transaction, using a given description, amount, category and time.
     *
     * @param description the given decription
     * @param amount the given amount
     * @param category the given category
     * @param time the given time
     */
    public Transaction(String description, double amount, String category, LocalDateTime time) {
        this(description, amount, category);
        setTime(time);
    }

    /**
     * Empty constructor.
     */
    public Transaction() {
        
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

    /**
     * Sets description for the transaction.
     *
     * @param description the given description
     */
    public void setDescription(String description) {
        if (description.equals("")) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        this.description = description;
    }

    /**
     * Sets amount for the transaction.
     *
     * @param amount the given amount
     */
    public void setAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.amount = amount;
    }

    /**
     * Sets category for the transaction.
     *
     * @param category the given category
     */
    public void setCategory(String category) {
        if (category.equals("")) {
            throw new IllegalArgumentException("Category cannot be empty.");
        }
        this.category = category;
    }

    /**
     * Sets time for the transaction.
     *
     * @param time the given time
     */
    public void setTime(LocalDateTime time) {
        if (time == null) {
            throw new IllegalArgumentException("Time is not set");
        }
        this.time = time;
    }

    @Override
    public String toString() {
        String transactionString = getAmount() + "    " 
            + getDescription() + "    " + getTime().toLocalDate();
        if (this instanceof Income) {
            return "+ " + transactionString;
        } else {
            return "- " + transactionString;
        }   
    }
    
}
