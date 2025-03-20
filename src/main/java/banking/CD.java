package banking;

public class CD extends Account{
    // starting limits for account
    private static final double MIN_BALANCE = 1000;
    private static final double MAX_BALANCE = 10000;
    private final int creationMonth;


    public CD(int accId, double apr, double balance, int creationMonth) {
        super(accId, apr, balance);

        this.creationMonth = creationMonth;

        //check for foundational errors with account creation
        if (balance < MIN_BALANCE || balance > MAX_BALANCE) {
            throw new IllegalArgumentException("CD balance must be between $" + MIN_BALANCE + " and $" + MAX_BALANCE);
        }
    }

    public int getCreationMonth() {
        return creationMonth;
    }

    @Override
    public boolean withdraw(double amount, int currentMonth) {
        // check for early withdrawal
        if(currentMonth - creationMonth < 12) {
            System.out.println("Cannot withdraw from CD before 12 months");
            return false;
        }

        //withdrawal limit
        if(amount < balance) {
            System.out.println("Must withdraw whole amount");
            return false;
        } else {
            //withdraw full amount
            super.setBalance(0);
        }
        
        return true;
    }

    @Override
    public boolean deposit(double money) {
        System.out.println("Cannot deposit to this account");
        return false;
    }

}

