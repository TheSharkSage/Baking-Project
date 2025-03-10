package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import javax.print.attribute.standard.MediaSize;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankTest {

    public static final int QUICK_ID = 12345678;
    public static final int QUICK_ID_2 = 87654321;
    public static final int MONEY = 20;
    private static final int QUICK_ID_3 = 12341234;
    Bank bank;
    public Account checking;
    public Account savings;
    public Account cd;

    @BeforeEach
    void setUp() {
        bank = new Bank();//make a new bank with each test case
        checking = new Checking(QUICK_ID);
        savings = new Savings(QUICK_ID_2);
        cd = new CD(QUICK_ID_3, 10);
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
        singleDeposit(checking,100, QUICK_ID);
        bank.findAccount(QUICK_ID).withdraw(90);
        assertEquals(10,  bank.findAccount(QUICK_ID).getBalance());
    }

    @Test
    public void withdraw_through_bank_multiple_times() {
        singleDeposit(checking, MONEY, QUICK_ID);
        bank.findAccount(QUICK_ID).withdraw(13.8);
        bank.findAccount(QUICK_ID).withdraw(9.3);//withdrawing past the amount to ensure the withdrawal still operates properly
        double actual = bank.findAccount(QUICK_ID).getBalance();

        assertEquals(0,actual);
    }

    @Test
    public void cannot_withdraw_more_than_400_from_checking() {
        singleDeposit(checking, MONEY, QUICK_ID);
        bank.findAccount(QUICK_ID).withdraw(500);

        double actual = bank.findAccount(QUICK_ID).getBalance();

        assertEquals(MONEY, actual);
    }

    @Test
    public void cannot_withdraw_more_than_1000_from_savings() {
        singleDeposit(savings, MONEY, QUICK_ID_2);
        bank.findAccount(QUICK_ID).withdraw(1000);

        double actual = bank.findAccount(QUICK_ID).getBalance();

        assertEquals(MONEY, actual);
    }

    @Test
    public void cd_can_only_accept_full_withdrawal() {
        bank.addAccount(cd);
        bank.findAccount(QUICK_ID_3).deposit(MONEY);
        bank.findAccount(QUICK_ID_3).withdraw(10);

        double actual = bank.findAccount(QUICK_ID_3).getBalance();

        assertEquals(MONEY, actual);
    }

    @Test
    public void cannot_deposit_more_than_1000_in_checking() {
        singleDeposit(checking, 1001, QUICK_ID);
        double actual = bank.findAccount(QUICK_ID).getBalance();
        assertEquals(0,actual );
    }

    @Test
    public void cd_cannot_receive_deposit() {
        singleDeposit(cd, 100, QUICK_ID_3);
        double actual = bank.findAccount(QUICK_ID_3).getBalance();
        assertEquals(0, actual);
    }

    @Test
    public void close_an_account() {
        bank.addAccount(checking);
        bank.removeAccount(checking);

        assertTrue(bank.getAccounts().isEmpty());
    }
    //helper methods
    private void singleDeposit(Account account, double money, int id) {
        bank.addAccount(account);
        bank.findAccount(id).deposit(money);
    }


}
