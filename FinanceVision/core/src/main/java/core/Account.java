package core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Contains logic for an account.
 */
public class Account {
    
    private double startValue;
    private List<Transaction> transactions;

    /**
     * Creates a new account, using a given start value.
     *
     * @param startValue the given start value
     */
    public Account(double startValue) {
        this.startValue = startValue;
        transactions = new ArrayList<>();
    }

    /**
     * Empty constructor.
     */
    public Account() {

    }
    
    /** 
     * Returns total bank balance for the account.
     *
     * @return total bank balance for the account
     */
    public double getBalance() {
        double balance = startValue;
        for (Transaction t : transactions) {

            if (t instanceof Income) {
                balance += t.getAmount();
            } else if (t instanceof Expense) {
                balance -= t.getAmount();
            }
        }

        return balance;

    }

    public void setStartValue(double startValue) {
        this.startValue = startValue;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public double getStartValue() {
        return startValue;
    }

    /**
     * Adds a transaction to the account.
     *
     * @param t transaction to add
     */
    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    /**
     * Removes a transaction from the account.
     *
     * @param t transaction to remove
     */
    public void removeTransaction(Transaction t) {
        transactions.remove(t);
    }

    public List<Transaction> getIncomes() {
        return getTransactions(t -> t instanceof Income);
    }

    public List<Transaction> getExpenses() {
        return getTransactions(t -> t instanceof Expense);
    }

    public List<Transaction> getTransactions(Predicate<Transaction> p) {
        return transactions.stream().filter(p).toList();
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

}
