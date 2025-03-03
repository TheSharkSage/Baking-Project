package banking;

public class CD extends Account{
    public CD(int accountId, double apr) {
        super(accountId, 0, apr);
    }

    public CD(int accId, double apr, double balance) {
        super(accId, apr, balance);
    }

}

