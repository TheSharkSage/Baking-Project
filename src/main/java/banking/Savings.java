package banking;

public class Savings extends Account{
    public Savings(int accId) {
        super(accId, 0);
    }

    public Savings(int accId, double balance) {
        super( accId, balance );
    }

    public Savings(int accId, double balance, double apr) {
        super( accId, balance, apr);
    }

    @Override
    public void deposit(double money) {
        if (money > 2500) {
            System.out.println("Amount exceeds 2500");
        }

        super.setBalance(super.getBalance() + money);
    }
}
