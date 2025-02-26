import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import javax.print.attribute.standard.MediaSize;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankTest {

    public static final String QUICK_ID = "12345678";
    public static final String QUICK_ID_2 = "87654321";
    Bank bank;
    public Account checking;
    public Account savings;

    @BeforeEach
    void setUp() {
        bank = new Bank();//make a new bank with each test case
        checking = new Checking(QUICK_ID);
        savings = new Savings(QUICK_ID_2);
    }


    @Test
    public void bank_has_no_accounts() {
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    public void add_account_to_bank() {
        bank.addAccount(checking);
        assertEquals(checking, bank.findAccount(QUICK_ID));
    }

    @Test
    public void add_two_accounts_to_bank() {
        bank.addAccount(checking);
        bank.addAccount(savings);
        Account actual = bank.findAccount(QUICK_ID);
        Account actual2 = bank.findAccount(QUICK_ID_2);
        assertEquals(QUICK_ID , actual.getAccountId());
        assertEquals(QUICK_ID_2 , actual2.getAccountId());

    }

    @Test
    public void retrieve_one_account_from_bank() {
        bank.addAccount(checking);
        Account actual = bank.findAccount(QUICK_ID);
        assertEquals(QUICK_ID, actual.getAccountId());//bank class->getAccounts method->get ID from hash map-> retrieve name from checking class method
    }

    @Test
    public void deposit_to_specific_account_by_ID() {
        //if we want to ensure that we are depositing to the correct account, we'd compare the id's of our expected vs actual
        bank.addAccount(checking);
        bank.addAccount(savings);

        bank.findAccount(QUICK_ID).deposit(100);

        assertEquals(100, bank.findAccount(QUICK_ID).getBalance());
        assertEquals(0, bank.findAccount(QUICK_ID_2).getBalance());
    }

    @Test
    public void deposit_multiple_times() {
        bank.addAccount(checking);
        bank.findAccount(QUICK_ID).deposit(100);
        bank.findAccount(QUICK_ID).deposit(140);

        assertEquals(240, bank.findAccount(QUICK_ID).getBalance());
    }

    @Test
    public void withdrawal_to_specific_account_by_ID() {
        //if we want to ensure that we are depositing to the correct account, we'd compare the id's of our expected vs actual
        bank.addAccount(checking);
        bank.findAccount(QUICK_ID).deposit(100);
        bank.findAccount(QUICK_ID).withdraw(90);
        assertEquals(10,  bank.findAccount(QUICK_ID).getBalance());
    }

    @Test
    public void withdraw_through_bank_multiple_times() {
        bank.addAccount(checking);
        bank.findAccount(QUICK_ID).deposit(AccountTest.MONEY_AMOUNT);
        bank.findAccount(QUICK_ID).withdraw(13.8);
        bank.findAccount(QUICK_ID).withdraw(9.3);//withdrawing past the amount to ensure the withdrawal still operates properly
        double actual = bank.findAccount(QUICK_ID).getBalance();

        assertEquals(0,actual);
    }
}
