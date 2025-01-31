public abstract class Account {
    private double balance;
    public String name;

    public Account(String name, double startAmount) { //the name of the account
        this.name = name; //the current account holder's name
        this.balance=startAmount;
    }

    public String getName() {
        return name; //return the name of the account
    }

    public Account(double start_amount) {
        this.balance=start_amount;
    }

    public Account() {
        this(0);
    }

    public double getBalance() {
        return balance;

        //return -1; //used for failed test cases
    }

    public double getAPR() {
        return 10.0;
    }

    public void deposit(double money) {
        balance += money;


    }

    public void withdraw(double money) {
        if (balance >= money) {
            balance -= money;
        } else {
            balance = 0;
        }
    }

}
