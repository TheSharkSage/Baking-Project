package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
}
