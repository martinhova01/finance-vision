package core;

import java.time.LocalDateTime;

public class Expense extends Transaction {

    public Expense(String description, Double amount, String category) {
        super(description, amount,category);
    }
    
    public Expense(String description, double amount, String category, LocalDateTime time){
        super(description, amount, category, time);
    }

    public Expense() {
        
    }
}
