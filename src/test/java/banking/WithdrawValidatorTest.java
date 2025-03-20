package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class WithdrawValidatorTest {
    public static final int CHECKING_ID = 12345678;
    public static final double APR = 10.00;
    private WithdrawValidator withdrawValidator;
    private Bank bank;
    public Account checking;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        checking = new Checking(CHECKING_ID, APR);
        withdrawValidator = new WithdrawValidator(bank);
    }

    @Test
    void withdraw_negative_amount() {
        bank.addAccount(checking);
        boolean actual = withdrawValidator.validate("Withdraw 12345678 -100");
        assertFalse(actual);
    }
//todo debug
//    @Test
//    void multiple_transactions_sequence() {
//        bank.addAccount(checking);
//        assertTrue(withdrawValidator.validate("withdraw 12345678 100"));
//        assertTrue(withdrawValidator.validate("withdraw 12345678 500"));
//    }
}
