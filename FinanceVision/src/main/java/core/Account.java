package core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Account {
    
    private double startValue;
    private List<Transaction> transactions;


    public Account(double startValue) {
        this.startValue = startValue;
        transactions = new ArrayList<>();
    }

    public double getBalance(){
        double balance = startValue;
        for (Transaction t : transactions){

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
        transactions.add(t);
    }

    public List<Transaction> getIncomes(){
        return getTransactions(t -> t instanceof Income);
    }

    public List<Transaction> getExpenses(){
        return getTransactions(t -> t instanceof Expense);
    }

    public List<Transaction> getTransactions(Predicate<Transaction> p){
        return transactions.stream().filter(p).toList();
    }

    
}
