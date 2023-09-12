package core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Account {
    
    private double startValue;
    private List<Transaction> transtactions;


    public Account(double startValue) {
        this.startValue = startValue;
        transtactions = new ArrayList<>();
    }

    public double getBalance(){
        double balance = startValue;
        for (Transaction t : transtactions){

            if (t instanceof Income){
                balance += t.getAmount();
            }
            else if (t instanceof Expense){
                balance -= t.getAmount();
            }
        }

        return balance;

    }

    public void addTransaction(Transaction t){
        transtactions.add(t);
    }

    public List<Transaction> getIncomes(){
        return getTransactions(t -> t instanceof Income);
    }

    public List<Transaction> getExpenses(){
        return getTransactions(t -> t instanceof Expense);
    }

    public List<Transaction> getTransactions(Predicate<Transaction> p){
        return transtactions.stream().filter(p).toList();
    }

    
}
