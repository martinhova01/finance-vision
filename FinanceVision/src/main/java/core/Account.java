package core;


public class Account {
    
    private double balance;
    private List<Transaction> transtactions;


    public Account(double balance) {
        this.balance = balance;
        transtactions = new ArrayList<>();
    }

    public double getBalance(){
        return balance;
    }

    public List<Transaction> getIncomes(){
        return transtactions.stream().filter(a -> a instanceof Income).toList();

    }

    
}
