package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import javax.print.attribute.standard.MediaSize;
import static org.junit.jupiter.api.Assertions.*;


public class BankTest {

    public static final int CHECKING_ID = 12345678;
    public static final int SAVINGS_ID = 87654321;
    public static final int MONEY = 20;
    private static final int CD_ID = 12341234;
    Bank bank;
    public Account checking;
    public Account savings;
    public Account cd;

    @BeforeEach
    void setUp() {
        bank = new Bank();//make a new bank with each test case
        checking = new Checking(CHECKING_ID);
        savings = new Savings(SAVINGS_ID);
        cd = new CD(CD_ID, 10);
    }


    @Test
    public void bank_has_no_accounts() {
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    public void add_account_to_bank() {
        bank.addAccount(checking);
        assertTrue(bank.accountExistsByID(CHECKING_ID));
        assertEquals(checking, bank.findAccount(CHECKING_ID));
    }

    @Test
    public void add_two_accounts_to_bank() {
        bank.addAccount(checking);
        bank.addAccount(savings);

        assertEquals(checking, bank.findAccount(CHECKING_ID));
        assertEquals(savings, bank.findAccount(SAVINGS_ID));

    }

    @Test
    public void retrieve_one_account_from_bank() {
        bank.addAccount(checking);
        Account actual = bank.findAccount(CHECKING_ID);
        assertEquals(CHECKING_ID, actual.getAccountId());//bank class->getAccounts method->get ID from hash map-> retrieve name from checking class method
    }

    @Test
    public void deposit_to_specific_account_by_ID() {
        //if we want to ensure that we are depositing to the correct account, we'd compare the id's of our expected vs actual
        bank.addAccount(checking);
        bank.addAccount(savings);

        bank.findAccount(CHECKING_ID).deposit(100);

        assertEquals(100, bank.findAccount(CHECKING_ID).getBalance());
        assertEquals(0, bank.findAccount(SAVINGS_ID).getBalance());
    }

    @Test
    public void deposit_multiple_times() {
        bank.addAccount(checking);
        bank.findAccount(CHECKING_ID).deposit(100);
        bank.findAccount(CHECKING_ID).deposit(140);

        assertEquals(240, bank.findAccount(CHECKING_ID).getBalance());
    }

    @Test
    public void withdrawal_to_specific_account_by_ID() {
        //if we want to ensure that we are depositing to the correct account, we'd compare the id's of our expected vs actual
        singleDeposit(checking,100, CHECKING_ID);
        bank.findAccount(CHECKING_ID).withdraw(90);
        assertEquals(10,  bank.findAccount(CHECKING_ID).getBalance());
    }

    @Test
    public void withdraw_through_bank_multiple_times() {
        singleDeposit(checking, MONEY, CHECKING_ID);
        bank.findAccount(CHECKING_ID).withdraw(13.8);
        bank.findAccount(CHECKING_ID).withdraw(9.3);//withdrawing past the amount to ensure the withdrawal still operates properly
        double actual = bank.findAccount(CHECKING_ID).getBalance();

        assertEquals(0,actual);
    }

    @Test
    public void cannot_withdraw_more_than_400_from_checking() {
        singleDeposit(checking, MONEY, CHECKING_ID);


        boolean actual = checking.withdraw(500);

        assertFalse(actual);
    }

    @Test
    public void cannot_withdraw_more_than_1000_from_savings() {
        singleDeposit(savings, 2000, SAVINGS_ID);

        boolean actual = savings.withdraw(1100);
        assertFalse(actual);
    }

    @Test
    public void cd_can_only_accept_full_withdrawal() {
        cd = new CD(CD_ID, MONEY, 10);
        bank.addAccount(cd);

        boolean actual = cd.withdraw(10);

        assertFalse(actual);
        assertTrue(cd.withdraw(MONEY));
    }

    @Test
    public void cannot_deposit_more_than_1000_in_checking() {
        boolean actual = checking.deposit(1001);
        assertFalse(actual);
    }

    @Test
    public void cd_cannot_receive_deposit() {
        singleDeposit(cd, 100, CD_ID);
        boolean actual = cd.deposit(100);
        assertFalse(actual);
    }

    @Test
    public void close_an_account() {
        bank.addAccount(checking);
        bank.removeAccount(checking);

        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    public void cannot_deposit_more_than_2500_in_savings() {
        boolean actual = savings.deposit(2600);
        assertFalse(actual);
    }

    @Test
    public void transfer_between_accounts() {
        bank.addAccount(checking);
        bank.addAccount(savings);
        //singleDeposit(checking, 100, CHECKING_ID);
        checking.deposit(100);

        boolean actual = bank.transfer(CHECKING_ID, SAVINGS_ID, 100);
        assertTrue(actual);
        assertEquals(100, savings.getBalance());

    }

    @Test
    public void transfer_between_same_account_types() {
        Account checking2 = new Checking(12341234, 100);
        bank.addAccount(checking);
        bank.addAccount(checking2);

        bank.transfer(12341234, CHECKING_ID, 50);
        double actual = bank.findAccount(CHECKING_ID).getBalance();
        assertEquals(50, actual);
    }

    @Test
    public void transfer_exceeds_account_balance_is_valid() {
        Account checking2 = new Checking(12341234, 100);
        bank.addAccount(checking);
        bank.addAccount(checking2);

        boolean actual = bank.transfer(12341234, CHECKING_ID, 150);
        assertTrue(actual);
    }

    @Test
    public void cd_refuses_transfer() {
        Account cd = new CD(CD_ID, MONEY, 10);
        bank.addAccount(cd);
        bank.addAccount(savings);
        singleDeposit(savings, MONEY, SAVINGS_ID);

        boolean actual = bank.transfer(SAVINGS_ID, CD_ID, 10);
        assertFalse(actual);
    }

    @Test
    public void cd_transfer_to_account_refused() {
        Account cd = new CD(CD_ID, MONEY, 10);
        bank.addAccount(cd);
        bank.addAccount(savings);
        singleDeposit(savings, MONEY, SAVINGS_ID);

        boolean actual = bank.transfer(CD_ID, SAVINGS_ID, 10);
        assertFalse(actual);
    }




    //helper methods
    private void singleDeposit(Account account, double money, int id) {
        bank.addAccount(account);
        bank.findAccount(id).deposit(money);
    }


}
