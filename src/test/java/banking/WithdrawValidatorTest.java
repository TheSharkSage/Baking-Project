package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WithdrawValidatorTest {
    public static final int CHECKING_ID = 12345678;
    private WithdrawValidator withdrawValidator;
    private Bank bank;
    public Account checking;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        checking = new Checking(CHECKING_ID);
        withdrawValidator = new WithdrawValidator(bank);
    }

    @Test
    void withdraw_negative_amount() {
        bank.addAccount(checking);
        boolean actual = withdrawValidator.validate("Withdraw -100");
        assertFalse(actual);
    }

    @Test
    void multiple_transactions_sequence() {
        bank.addAccount(checking);
        assertTrue(withdrawValidator.validate("withdraw 12345678 100"));
        assertTrue(withdrawValidator.validate("withdraw 12345678 500"));
    }
}
