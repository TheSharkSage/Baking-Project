package banking;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;


public class Bank {
    private Map<Integer, Account> accounts;
    private TimeService timeService; // implement a time service for the bank to call on

    // constructor for time framework
    public Bank() {
        this(new BankTimeService());
    }

    //dependency injection
    public Bank(TimeService timeService) {
        accounts = new HashMap<>();
        this.timeService = timeService;
    }


    public Map<Integer, Account> getAccounts() {//a list of all the accounts stored, with a key to each account
        return new HashMap<>(accounts);//retrieve bank account info
    }
    //make a method that looks for an id based off a number

    //Make a method for each account that can be made

    public int getCurrentMonth() {
        return timeService.getCurrentMonth();
    }

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

    // Returns true if the withdrawal was successful
    // Returns false if the account does not exist or insufficient funds
    public boolean withdraw(int accountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than 0");
        }

        Account account = findAccount(accountId);
        if (account == null) {
            System.out.println("Account does not exist");
            return false;
        }

        //all account subclasses handle amount exceeding the balance, no need to check
        return account.withdraw(amount, timeService.getCurrentMonth());
    }

    public boolean transfer(int fromAccountId, int toAccountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than 0");
        }
        
        Account toAccount = findAccount(toAccountId);
        Account fromAccount = findAccount(fromAccountId);

        // Check if the accounts exist
        if(toAccount == null || fromAccount == null){
            return false;
        }

        // Transfer the as much money from the account as possible if the amount exceeds balance
        if (amount > fromAccount.getBalance()){
            System.out.println("Insufficient funds, transferred max possible value");
            //return false;
            //deposit the balance of the account to the new account and then withdraw from the whole account
            return toAccount.deposit(fromAccount.getBalance()) && fromAccount.withdraw(fromAccount.getBalance(), timeService.getCurrentMonth());
        }

        // perform the transfer
        //if there is an error with the withdrawal or deposit, the transfer won't execute
        return fromAccount.withdraw(amount, timeService.getCurrentMonth()) && toAccount.deposit(amount);

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


    // Time passing functionality
    public void passTime(int numMonths) {
        for (int i=0; i < numMonths; i++) {
            // advance months with each iteration
            timeService.advanceMonth(numMonths);
            closeEmptyAccounts();
            deductFromLowAccounts();
            accrueAPR();
        }
    }

    private void closeEmptyAccounts() {
        List<Account> accountsToRemove = new ArrayList<>();
        
        // mark accounts with 0 balance for removal
        for(Account a : accounts.values()) {
            if (a.getBalance() == 0){
                accountsToRemove.add(a);
            }
        }

        // remove accounts with 0 balance
        for (Account a : accountsToRemove) {
            removeAccount(a);
        }
    }

    private void deductFromLowAccounts() {       
        for (Account a : accounts.values()) {
            if(a.getBalance() < 100) {
                // doesnt interfere w/ savings account restrictions
                a.setBalance(a.getBalance() - 25);
            }
        }
    }

    private void accrueAPR() {
        //Todo iterate for cd accounts and accrue the apr 4 times within a month
        //List<Account> cdAccountsToAccrue = new ArrayList<>();

        for (Account a: accounts.values()) {
            // retrieve apr, convert to a percentage, divide by months ina year
            double monthlyInterest = (a.getAPR()/100)/12;
            double monthlyYield = a.getBalance() * monthlyInterest; 
            deposit(a.getAccountId(), monthlyYield);
        }
        // CD needs to accrue four times within a month

    }
}

