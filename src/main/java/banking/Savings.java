package banking;

public class Savings extends Account{
    private int lastWithdrawalMonth;

    public Savings(int accId, double apr) {
        super( accId, apr);
        lastWithdrawalMonth = -1;
    }

    @Override
    public boolean deposit(double money) {
        if (money > 2500) {
            System.out.println("Amount exceeds 2500");
            return false;
        }

        super.setBalance(super.getBalance() + money);
        return true;
    }

    @Override
    public boolean withdraw(double money, int currentMonth) {
        if(lastWithdrawalMonth == currentMonth) {
            System.out.println("Cannot withdraw more than once in a month");
            return false;
        }

        //withdrawal limit
        if(money > 2500) {
            System.out.println("Cannot withdraw More than 1000 at a time");
            return false;
        }

        if (balance >= money) {
            super.setBalance(super.getBalance() - money);
        } else {
            //if the amount exceeds the balance, then take as much money from the account as possible
            super.setBalance(0);
        }

        lastWithdrawalMonth = currentMonth;
        return true;

    }
}
