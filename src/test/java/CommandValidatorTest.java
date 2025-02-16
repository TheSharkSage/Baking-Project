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

}
