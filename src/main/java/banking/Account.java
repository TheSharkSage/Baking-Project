package banking;

public abstract class Account {
    public double balance;
    public final int accountId;
    public double APR;

    public Account(int accountId, double startAmount) { //the name of the account
        this.accountId = accountId; //the current account holder's name
        this.balance = startAmount;
    }
    
    //overloaded constructor
    public Account(int accountId, double startAmount, double apr) { //the name of the account
        this.accountId = accountId; //the current account holder's name
        this.balance = startAmount;
        this.APR = apr;
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


    public void deposit(double money) {
        setBalance(getBalance() + money);
    }

    public void withdraw(double money) {
        if (balance >= money) {
            setBalance(getBalance() - money);
        } else {
            balance = 0;
        }
    }

}
