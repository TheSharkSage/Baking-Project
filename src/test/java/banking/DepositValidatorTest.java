package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepositValidatorTest {
    public static final int ID = 87654321;
    public CommandValidator depositValidator;

    public Bank bank;
    public Account checking;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        checking = new Checking(ID);
        depositValidator = new CommandValidator(bank);
    }

    @Test
    void deposit_to_empty_bank() {
        //bank starts as empty in the setup
        boolean actual = depositValidator.validate("deposit 12345678 20.00");
        assertFalse(actual);
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
    void deposit_negative_value() {
        bank.addAccount(checking);
        boolean actual = depositValidator.validate("Deposit 12345678 -100.00");
        assertFalse(actual);
    }

    @Test
    void deposit_command_has_typo() {
        boolean actual = depositValidator.validate("deeposi 12345678 20.00");
        assertFalse(actual);
    }

    @Test
    void deposit_command_in_wrong_order() {
        boolean actual = depositValidator.validate("12345678 20.00 deposit");
        assertFalse(actual);
    }

    @Test
    void deposit_incorrect_amount_type() {
        boolean actual = depositValidator.validate("deposit 12345678 twenty dollars");
        assertFalse(actual);
    }

    @Test
    void deposit_to_savings_exceeds_2500() {
        boolean actual = depositValidator.validate("deposit 12345678 3000.00");
        assertFalse(actual);
    }

    @Test
    void deposit_to_nonexistent_account() {
        boolean actual = depositValidator.validate("Deposit 987654321 20");
        assertFalse(actual);
    }


    
}
