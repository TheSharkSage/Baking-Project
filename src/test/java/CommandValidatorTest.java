import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandValidatorTest {
    public static final String ID = "12345678";
    CommandValidator commandValidator;
    Bank bank;
    private Account checkings;
    private Account savings;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        checkings = new Checkings(ID);
        commandValidator = new CommandValidator(bank);
    }

    @Test
    void valid_command() {
        boolean actual = commandValidator.validate("Create checkings 12345678");
        assertTrue(actual);
    }

    @Test
    void duplicate_account_id_is_invalid() {
        //savings = new Savings(ID);
        bank.addAccount(checkings);
        boolean actual = commandValidator.validate("Create Savings 12345678");
        assertFalse(actual);
    }

    @Test
    void create_account_is_spelled_wrong() {
        boolean actual = commandValidator.isValidCommand("kreeight checkings 12345678");
        assertFalse(actual);
    }

    @Test
    void account_type_is_invalid() {
        boolean actual = commandValidator.isValidAccount("Create highyieldsavings 12345678");
        assertFalse(actual);
    }

    @Test
    void account_id_out_of_bounds() {
        boolean actual = commandValidator.validate("Create Savings 123456787654321");
        assertFalse(actual);
    }

    @Test
    void missing_account_id() {
        boolean actual = commandValidator.validate("create checkings ");
        assertFalse(actual);
    }

    @Test
    void null_command_is_invalid() {
        boolean actual = commandValidator.validate(null);
        assertFalse(actual);
    }

    @Test
    void empty_command_is_invalid() {
        boolean actual = commandValidator.validate("");
        assertFalse(actual);
    }

    @Test
    void command_with_extra_spaces_is_invalid() {
        boolean actual = commandValidator.validate("Create  Savings  12345678");
        assertFalse(actual);
    }

    @Test
    void cd_invalid_apr_with_letters_is_invalid() {
        boolean actual = commandValidator.validate("Create CD 12345678 ABC%");
        assertFalse(actual);
    }

    @Test
    void cd_negative_apr_is_invalid() {
        boolean actual = commandValidator.validate("Create CD 12345678 -5.0");
        assertFalse(actual);
    }

    @Test
    void cd_invalid_initial_amount_with_letters_is_invalid() {
        boolean actual = commandValidator.validate("Create CD 12345678 5.0 ABC");
        assertFalse(actual);
    }

    @Test
    void cd_negative_initial_amount_is_invalid() {
        boolean actual = commandValidator.validate("Create CD 12345678 5.0 -1000");
        assertFalse(actual);
    }

    @Test
    void cd_valid_parameters_is_valid() {
        boolean actual = commandValidator.validate("Create CD 12345678 5.0 1000");
        assertTrue(actual);
    }

    @Test
    void cd_missing_apr_is_invalid() {
        boolean actual = commandValidator.validate("Create CD 12345678");
        assertFalse(actual);
    }

    @Test
    void cd_missing_initial_amount_is_invalid() {
        boolean actual = commandValidator.validate("Create CD 12345678 5.0");
        assertFalse(actual);
    }

    @Test
    void cd_extra_parameters_is_invalid() {
        boolean actual = commandValidator.validate("Create CD 12345678 5.0 1000 extraParam");
        assertFalse(actual);
    }

    @Test
    void cd_mixed_case_with_valid_parameters_is_valid() {
        boolean actual = commandValidator.validate("CrEaTe Cd 12345678 5.0 1000");
        assertTrue(actual);
    }

    @Test
    void cd_all_lowercase_with_valid_parameters_is_valid() {
        boolean actual = commandValidator.validate("create cd 12345678 5.0 1000");
        assertTrue(actual);
    }

    @Test
    void create_savings_with_invalid_parameters() {
        boolean actual = commandValidator.validate("Create savings 123abc45");
        assertFalse(actual);
    }

    @Test
    void create_cd_without_money() {
        boolean actual = commandValidator.validate("Create CD 0");
        assertFalse(actual);
    }

    @Test
    void create_account_with_invalid_characters_in_name() {
        boolean actual = commandValidator.validate("Create checking 12345678 C@rm3l0");
        assertFalse(actual);
    }

    @Test
    void create_account_with_too_many_numbers() {
        boolean actual = commandValidator.validate("Create Saving 1123458909876543321 Carmelo");
        assertFalse(actual);
    }

    @Test
    void deposit_with_cents() {
        bank.addAccount(checkings);
        boolean actual = commandValidator.validate("Deposit 12345678 20.64");
        assertTrue(actual);
    }

    @Test
    void deposit_large_amount() {
        bank.addAccount(checkings);
        boolean actual = commandValidator.validate("Deposit 12345678 999999999.99");
        assertTrue(actual);
    }

    @Test
    void withdraw_large_amount() {
        bank.addAccount(checkings);
        checkings.deposit(1000000000.00); // Ensure sufficient balance
        boolean actual = commandValidator.validate("Withdraw 12345678 999999999.99");
        assertTrue(actual);
    }

    @Test
    void deposit_to_nonexistent_account() {
        boolean actual = commandValidator.validate("Deposit 987654321 20");
        assertFalse(actual);
    }

    @Test
    void withdraw_negative_amount() {
        bank.addAccount(checkings);
        checkings.deposit(100.0);
        boolean actual = commandValidator.validate("Withdraw -100");
        assertFalse(actual);
    }

    @Test
    void multiple_transactions_sequence() {
        bank.addAccount(checkings);

        assertTrue(commandValidator.validate("Deposit 12345678 100"));
        assertTrue(commandValidator.validate("Deposit 12345678 500"));
        assertTrue(commandValidator.validate("Withdraw 12345678 300"));
        assertTrue(commandValidator.validate("Withdraw 12345678 73"));
    }

    @Test
    void multiple_withdrawals_to_zero() {
        bank.addAccount(checkings);
        checkings.deposit(100.0);

        assertTrue(commandValidator.validate("Withdraw 12345678 100"));
        assertTrue(commandValidator.validate("Withdraw 12345678 100"));
    }

    @Test
    void create_account_with_apr_exceeding_ten() {
        boolean actual = commandValidator.validate("Create checkings 12345678 11.0");
        assertFalse(actual);
    }

    @Test
    void get_apr_missing_account() {
        boolean actual = commandValidator.validate("getAPR");
        assertFalse(actual);
    }

}
