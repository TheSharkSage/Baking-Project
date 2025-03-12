package banking;

public class Checking extends Account {
    //private double balance;

    //constructors
    public Checking(int accId) {
        super( accId, 0);
    }

    public Checking(int accId, double balance) {
        super( accId, balance );
    }

    public Checking(int accId, double balance, double apr) {
        super( accId, balance, apr);
    }



    @Override
    public boolean withdraw(double money) {
        //withdrawal limit
        if(money > 400) {
            System.out.println("Cannot withdraw More than 400 at a time");
            return false;
        }

        if (balance >= money) {
            super.setBalance(super.getBalance() - money);
        } else {
            balance = 0;
        }
        return true;
    }

    @Override
    public boolean deposit(double money) {
        if (money > 1000) {
            System.out.println("Deposit amount exceeds 1000");
            return false;
        }
        super.setBalance(super.getBalance() + money);
        return true;
    }

}
