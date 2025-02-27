public class Checking extends Account {
    public Checking(int accId) {
        super( accId, 0);
    }

    public Checking(int accId, double balance) {
        super( accId, balance );
    }

    public Checking(int accId, double balance, double apr) {
        super( accId, balance, apr);
    }
}
