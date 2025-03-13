package banking;

public abstract class Account {
    protected double balance;
    protected final int accountId;
    protected double APR;


    // constructor for saving and checking
    public Account(int accountId, double apr) { //the name of the account
        this.accountId = accountId; //the current account holder's name
        this.APR = apr;
        this.balance = 0;
    }
    
    //overloaded constructor for CD
    public Account(int accountId, double apr, double startAmount) { //the name of the account
        this.accountId = accountId; //the current account holder's name
        this.APR = apr;
        this.balance = startAmount;

    }


    public int getAccountId() {
        return accountId; //return the name of the account
    }


    public double getBalance() {
        return balance;

        //return -1; //used for failed test cases
    }

    public void setBalance(double newBalance) {
        this.balance = newBalance;
    }
    public double getAPR() {
        return APR;
    }

    public void setAPR(double setAPR) {
        this.APR = setAPR;
    }


    public boolean deposit(double money) {
        setBalance(getBalance() + money);
        return true;
    }

    // public boolean withdraw(double money) {
    //     if (balance >= money) {
    //         setBalance(getBalance() - money);
    //         return true;
    //     } else {
    //         balance = 0;
    //         return true;
    //     }
    // }

    public abstract boolean withdraw(double money, int currentMonth);
}
