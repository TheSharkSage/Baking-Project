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
            throw new IllegalArgumentException("Account cannot be null");
        }
        accounts.put(account.getAccountId(), account);
    }

    public void removeAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        accounts.remove(account.getAccountId(), account);
    }

    // Returns true if the deposit was successful
    // Returns false if the account does not exist
    public boolean deposit(int accountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than 0");
        }

        Account account = findAccount(accountId);
        if (account == null) {
            System.out.println("Account does not exist");
            return false;
        }

        account.deposit(amount);

        return true;
    }

    // Returns true if the withdrawl was successful
    // Returns false if the account does not exist or insufficient funds
    public boolean withdraw(int accountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawl amount must be greater than 0");
        }

        Account account = findAccount(accountId);
        if (account == null) {
            System.out.println("Account does not exist");
            return false;
        }

        if (amount >= account.getBalance()){
            System.out.println("Insufficient funds");
            return false;
        }
        
        account.withdraw(amount);
        return true;
    }

    public boolean transfer(int fromAccountId, int toAccountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawl amount must be greater than 0");
        }
        
        Account toAccount = findAccount(toAccountId);
        Account fromAccount = findAccount(fromAccountId);

        // Check if the accounts exist
        if(toAccount == null || fromAccount == null){
            return false;
        }

        // Check if the fromAccount has enough money
        if (amount >= fromAccount.getBalance()){
            System.out.println("Insufficient funds");
            return false;
        }

        // perform the transfer
        //if there is an error with the withdrawal or deposit, the transfer won't execute
        return fromAccount.withdraw(amount) && toAccount.deposit(amount);

        //return true;
    }

    //Command method to ask for account
    public Account findAccount(int accountId) {
        // check if map has address to accountId
        return accounts.get(accountId);
    }

    //Query method to check for existence
    public boolean accountExistsByID(int accountId) {
        // check if map has entry with key accountId
        return accounts.containsKey(accountId);
    }



}

