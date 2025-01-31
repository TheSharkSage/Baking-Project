import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, Account> accounts = new HashMap<>();

    public Bank(String ID)  {
        accounts = new HashMap<>();//a key for the account in the bank
    }
    public Map<String, Account> getAccounts() {//a list of all the accounts stored, with a key to each account
        return accounts;//retrieve bank account info
    }

    //Make a method for each account that can be made

    public void addCheckings(String quickId, String name) { //add an account by using the keys of an id and a name
        accounts.put(quickId, new Checkings(name));
    }

    public void addSavings(String quickId, String name) {
        accounts.put(quickId, new Savings(name));
    }

    public void addCD(String quickId, double amount) {
        accounts.put(quickId, new CD(amount));
    }
}
