public class CD extends Account{
    public CD(int accountId, double apr) {
        super(accountId, 0, apr);
    }

    public CD(int accId, double balance, double apr) {
        super(accId, balance, apr);
    }

}

