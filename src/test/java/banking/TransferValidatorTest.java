package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransferValidatorTest {
    public static final int CHECKING_ID = 12345678;
    public static final int SAVINGS_ID = 87654321;
    private CommandValidator transferValidator;

    public Bank bank;
    public Account checking;
    public Account savings;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        checking = new Checking(CHECKING_ID);
        savings = new Savings(SAVINGS_ID);
        transferValidator = new CommandValidator(bank);
    }

    @Test
    void valid_transfer_command() {
        bank.addAccount(checking);
        bank.addAccount(savings);
        boolean actual = transferValidator.validate("Transfer 12345678 87654321 100");
        assertTrue(actual);
    }

    @Test
    void transfer_between_empty_accounts() {
        boolean actual = transferValidator.validate("Transfer 12345678 87654321 100");
        assertFalse(actual);
    }

    @Test
    void invalid_transfer_to_same_account() {
        boolean actual = transferValidator.validate("transfer 12345678 12345678 100");
        assertFalse(actual);
    }
}
