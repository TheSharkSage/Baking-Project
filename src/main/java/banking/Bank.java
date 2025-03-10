package banking;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    public Map<Integer, Account> accounts;

    public Bank() {
        accounts = new HashMap<>();
    }

    public Map<Integer, Account> getAccounts() {//a list of all the accounts stored, with a key to each account
        return new HashMap<>(accounts);//retrieve bank account info
    }
    //make a method that looks for an id based off a number

    //Make a method for each account that can be made

    public void addAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("banking.Account cannot be null");
        }
        accounts.put(account.getAccountId(), account);
    }

    public void removeAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("bank is already empty");
        }
        accounts.remove(account.getAccountId(), account);
    }

    public void deposit(int accountId, double amount) {
        Account account = findAccount(accountId);
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than 0");
        }
        account.setBalance(account.getBalance() + amount);
    }

    public void withdraw(int accountId, double amount) {
        Account account = findAccount(accountId);
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than 0");
        }
        account.setBalance(account.getBalance() - amount);
    }

    public void transfer(int accountId, int accountId2, double amount) {
        Account account1 = findAccount(accountId);
        Account account2 = findAccount(accountId2);
        //withdraw from acc1
        account1.withdraw(amount);

        //deposit to acc2
        account2.deposit(amount);
    }

    //Command method to ask for account

    public Account findAccount(int accountId) {
        return accounts.get(accountId);
    }

    //Query method to check for existence

    public boolean accountExistsByID(int accountId) {
        //check if account has already been made
        if (!accounts.containsKey(accountId)){
            return false;
        }
        return accounts.get(accountId) != null;//boolean to check the existence of account
    }



}

