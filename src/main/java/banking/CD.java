package banking;

public class CD extends Account{
    public CD(int accountId, double apr) {
        super(accountId, 0, apr);
    }

    public CD(int accId, double balance, double apr) {
        super(accId, balance, apr);
    }

    @Override
    public void withdraw(double money) {
        //withdrawal limit
        if(balance != money) {
            System.out.println("Must withdraw whole amount");
            return;
        } else {
            //withdraw full amount
            super.setBalance(super.getBalance() - money);
        }
    }

    @Override
    public void deposit(double money) {
        System.out.println("Cannot deposit to this account");
        return;
    }

}

