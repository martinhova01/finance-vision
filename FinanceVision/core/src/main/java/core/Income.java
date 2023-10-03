package core;

import java.time.LocalDateTime;

/**
 * A {@link Transaction} that is defined as an income.
 */
public class Income extends Transaction {

    public Income(String description, Double amount, String category) {
        super(description, amount, category);
    }

    public Income(String description, double amount, String category, LocalDateTime time) {
        super(description, amount, category, time);
    }

    public Income() {
        
    }
    
}
