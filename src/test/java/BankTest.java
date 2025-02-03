import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import javax.print.attribute.standard.MediaSize;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankTest {

    public static final String QUICK_ID = "12345678";
    public static final String NAME = "Carmelo";
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank(QUICK_ID);//make a new bank with each test case
    }

    @Test
    public void bank_has_no_accounts() {
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    public void add_account_to_bank() {
        bank.addCheckings(QUICK_ID, NAME);
        assertEquals(NAME, bank.getAccounts().get(QUICK_ID).getName());
    }

    @Test
    public void add_two_accounts_to_bank() {
        bank.addCheckings(QUICK_ID, NAME);
        bank.addSavings(QUICK_ID + "1", NAME + "1");
        assertEquals(NAME + "1", bank.getAccounts().get(QUICK_ID + "1").getName());
    }

    @Test
    public void retrieve_one_account_from_bank() {
        bank.addCheckings(QUICK_ID, NAME);

        assertEquals(NAME, bank.getAccounts().get(QUICK_ID).getName());//bank class->getAccounts method->get ID from hash map-> retrieve name from checkings class method
    }

    @Test
    public void deposit_to_specific_account_by_ID() {
        //if we want to ensure that we are depositing to the correct account, we'd compare the id's of our expected vs actual
        bank.addCheckings(QUICK_ID, NAME);
        bank.getAccounts().get(QUICK_ID).deposit(100);
        double actual = bank.getAccounts().get(QUICK_ID).getBalance();

        assertEquals(100, actual);
    }

    @Test
    public void deposit_multiple_times() {
        bank.addCheckings(QUICK_ID, NAME);
        bank.getAccounts().get(QUICK_ID).deposit(100);
        bank.getAccounts().get(QUICK_ID).deposit(140);
        double actual = bank.getAccounts().get(QUICK_ID).getBalance();

        assertEquals(240, actual);
    }

    @Test
    public void withdrawal_to_specific_account_by_ID() {
        //if we want to ensure that we are depositing to the correct account, we'd compare the id's of our expected vs actual
        bank.addCheckings(QUICK_ID, NAME);
        bank.getAccounts().get(QUICK_ID).deposit(100);
        bank.getAccounts().get(QUICK_ID).withdraw(90);
        double actual = bank.getAccounts().get(QUICK_ID).getBalance();

        assertEquals(10, actual);
    }

    @Test
    public void withdraw_through_bank_multiple_times() {
        bank.addCheckings(QUICK_ID, NAME);
        bank.getAccounts().get(QUICK_ID).deposit(AccountTest.MONEY_AMOUNT);
        bank.getAccounts().get(QUICK_ID).withdraw(13.8);
        bank.getAccounts().get(QUICK_ID).withdraw(9.3);//withdrawing past the amount to ensure the withdrawal still operates properly
        double actual = bank.getAccounts().get(QUICK_ID).getBalance();

        assertEquals(0,actual);
    }
}
