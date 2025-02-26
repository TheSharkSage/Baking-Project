public abstract class Account {
    private double balance;
    public final String accountId;
    public double APR;

    public Account(String accountId, double startAmount) { //the name of the account
        if(!isValidAccountId(accountId)) {
            throw new IllegalArgumentException("Account ID must bw 8 digits");
        }
        this.accountId = accountId; //the current account holder's name
        this.balance = startAmount;
}

    private boolean isValidAccountId(String accountId) {
        return accountId != null && accountId.matches("\\d{8}");
    }

    public String getAccountId() {
        return accountId; //return the name of the account
    }


    public double getBalance() {
        return balance;

        //return -1; //used for failed test cases
    }

    public void setBalance(double newBalance) {
        this.balance = newBalance;
    }
    public double getAPR() {
        return APR;
    }

    public void setAPR(double setAPR) {
        this.APR = setAPR;
    }


public void deposit(double money) {
        setBalance(getBalance() + money);
    }

    public void withdraw(double money) {
        if (balance >= money) {
            setBalance(getBalance() - money);
        } else {
            balance = 0;
        }
    }

}
