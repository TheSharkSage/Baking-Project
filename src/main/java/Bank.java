import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, Account> accounts = new HashMap<>();

    public Map<String, Account> getAccounts() {//a list of all the accounts stored, with a key to each account
        return new HashMap<>(accounts);//retrieve bank account info
    }
    //make a method that looks for an id based off a number

    //Make a method for each account that can be made

    public void addAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        accounts.put(account.getAccountId(), account);
    }

    public void deposit(String accountId, double amount) {
        Account account = findAccount(accountId);
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than 0");
        }
        account.setBalance(account.getBalance() + amount);
    }

    public void withdraw(String accountId, double amount) {
        Account account = findAccount(accountId);
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than 0");
        }
        account.setBalance(account.getBalance() - amount);
    }

    //Command method to ask for account

    public Account findAccount(String accountId) {
        Account account = accounts.get(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found: " + accountId);
        }
        return account;
    }

    //Query method to check for existence

    public boolean accountExistsByID(String accountId) {
        return accounts.get(accountId) != null;//boolean to check the existence of account
    }
}

