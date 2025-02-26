import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepositValidatorTest {
    public static final String ID = "87654321";
    public DepositValidator depositValidator;

    public Bank bank;
    public Account checking;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        checking = new Checking(ID);
        depositValidator = new DepositValidator(bank);
    }


    @Test
    void deposit_with_cents() {
        bank.addAccount(checking);
        boolean actual = depositValidator.validate("Deposit 12345678 20.64");
        assertTrue(actual);
    }

    @Test
    void deposit_large_amount() {
        bank.addAccount(checking);
        boolean actual = depositValidator.validate("Deposit 12345678 999999999.99");
        assertTrue(actual);
    }

    @Test
    void deposit_to_nonexistent_account() {
        boolean actual = depositValidator.validate("Deposit 987654321 20");
        assertFalse(actual);
    }

    @Test
    void withdraw_negative_amount() {
        bank.addAccount(checking);
        checking.deposit(100.0);
        boolean actual = depositValidator.validate("Withdraw -100");
        assertFalse(actual);
    }

    @Test
    void multiple_transactions_sequence() {
        bank.addAccount(checking);

        assertTrue(depositValidator.validate("Deposit 12345678 100"));
        assertTrue(depositValidator.validate("Deposit 12345678 500"));
    }

    //todo implement zombies approach
    
}
