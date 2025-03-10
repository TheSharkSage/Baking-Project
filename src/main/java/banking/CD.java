package banking;

public class CD extends Account{
    public CD(int accountId, double apr) {
        super(accountId, 0, apr);
    }

    public CD(int accId, double apr, double balance) {
        super(accId, apr, balance);
    }

    @Override
    public void withdraw(double money) {
        //withdrawal limit
        if(balance != money) {
            System.out.println("Must withdraw whole amount");
            return;
        } else {
            //withdraw full amount
            super.setBalance(0);
        }
    }

    @Override
    public void deposit(double money) {
        System.out.println("Cannot deposit to this account");
        return;
    }

}

