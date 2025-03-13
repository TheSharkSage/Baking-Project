package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankTimeServiceTest {
    private Bank bank;
    private TimeService timeService;
    private static final int CD_ID = 12341234;
    private static final int SAVINGS_ID = 87654321;

    @BeforeEach
    void setUp() {
        // set up an intance of the bankTimeService using the parent interface
        timeService = new BankTimeService();
        bank = new Bank(timeService);
    }

    @Test
    public void cd_withdraw_before_12_months_invalid() {
        // create cd instance
        Account cd = new CD(CD_ID, 5.0, 2000, bank.getCurrentMonth());
        bank.addAccount(cd);

        boolean actual = bank.withdraw(CD_ID, 2000);
        assertFalse(actual);
        assertEquals(2000, cd.getBalance());

    }

    @Test
    public void cd_withdraw_after_12_months_is_valid() {
        // create cd instance
        Account cd = new CD(CD_ID, 5.0, 2000, bank.getCurrentMonth());
        bank.addAccount(cd);

        bank.passTime(12);

        boolean actual = bank.withdraw(CD_ID, 2000);
        assertTrue(actual);
        assertEquals(0, cd.getBalance());
    }

    @Test
    void savings_withdrawal_frequency_limit() {
        // Create a Savings account
        Account savings = new Savings(SAVINGS_ID, 2.5);
        bank.addAccount(savings);

        // Deposit some money
        savings.deposit(1000);

        // First withdrawal in a month should succeed
        boolean firstWithdrawal = savings.withdraw(500, bank.getCurrentMonth());
        assertTrue(firstWithdrawal);
        assertEquals(500, savings.getBalance());

        // Second withdrawal in the same month should fail
        boolean secondWithdrawal = savings.withdraw(200, bank.getCurrentMonth());
        assertFalse(secondWithdrawal);
        assertEquals(500, savings.getBalance());

        // Pass a month
        bank.passTime(1);

        // Now withdrawal should succeed again
        boolean withdrawalNextMonth = savings.withdraw(200, bank.getCurrentMonth());
        assertTrue(withdrawalNextMonth);
        assertEquals(300, savings.getBalance());
    }


}
