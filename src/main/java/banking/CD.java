package banking;

public class CD extends Account{
    // starting limits for account
    private static final double MIN_BALANCE = 1000;
    private static final double MAX_BALANCE = 10000;

    public CD(int accId, double apr, double balance) {
        super(accId, apr, balance);

        //check for foundational errors with account creation
        if (balance < MIN_BALANCE || balance > MAX_BALANCE) {
            throw new IllegalArgumentException("CD balance must be between $" + MIN_BALANCE + " and $" + MAX_BALANCE);
        }
    }


    @Override
    public boolean withdraw(double money) {
        //withdrawal limit
        if(balance != money) {
            System.out.println("Must withdraw whole amount");
            return false;
        } else {
            //withdraw full amount
            super.setBalance(super.getBalance() - money);
        }
        return true;
    }

    @Override
    public boolean deposit(double money) {
        System.out.println("Cannot deposit to this account");
        return false;
    }

}

