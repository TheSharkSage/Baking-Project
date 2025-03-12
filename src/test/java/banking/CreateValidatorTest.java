package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateValidatorTest {
    private static final int ID = 12345678;
    public static final double APR = 10.00;
    private CommandValidator createValidator;

    private Bank bank;
    public Account checking;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        checking = new Checking(ID, APR);
        createValidator = new CreateValidator(bank);

    }

    @Test
    void valid_command() {
        boolean actual = createValidator.validate("Create checking 12345678 10");
        assertTrue(actual);
    }

    @Test
    void duplicate_account_id_is_invalid() {
        //banking.Account savings = new banking.Savings(ID);
        bank.addAccount(checking);
        boolean actual = createValidator.validate("Create banking.Savings 12345678 10");
        assertFalse(actual);
    }

    @Test
    void create_account_is_spelled_wrong() {
        boolean actual = createValidator.validate("kreeight checking 12345678 10");
        assertFalse(actual);
    }

    @Test
    void account_type_is_invalid() {
        boolean actual = createValidator.validate("Create highyieldsavings 12345678 10");
        assertFalse(actual);
    }

    @Test
    void account_id_out_of_bounds() {
        boolean actual = createValidator.validate("Create Savings 123456787654321 10");
        assertFalse(actual);
    }

    @Test
    void missing_account_id() {
        boolean actual = createValidator.validate("create checking  10");
        assertFalse(actual);
    }

    @Test
    void null_command_is_invalid() {
        boolean actual = createValidator.validate(null);
        assertFalse(actual);
    }

    @Test
    void empty_command_is_invalid() {
        boolean actual = createValidator.validate("");
        assertFalse(actual);
    }

    @Test
    void command_with_extra_spaces_is_invalid() {
        boolean actual = createValidator.validate("Create  Savings  12345678  10");
        assertFalse(actual);
    }

    @Test
    void cd_invalid_apr_with_letters_is_invalid() {
        boolean actual = createValidator.validate("Create CD 12345678 ABC%");
        assertFalse(actual);
    }

    @Test
    void cd_negative_apr_is_invalid() {
        boolean actual = createValidator.validate("Create CD 12345678 -5.0");
        assertFalse(actual);
    }

    @Test
    void cd_invalid_initial_amount_with_letters_is_invalid() {
        boolean actual = createValidator.validate("Create CD 12345678 5.0 ABC");
        assertFalse(actual);
    }

    @Test
    void cd_negative_initial_amount_is_invalid() {
        boolean actual = createValidator.validate("Create CD 12345678 5.0 -1000");
        assertFalse(actual);
    }

    @Test
    void cd_valid_parameters_is_valid() {
        boolean actual = createValidator.validate("Create CD 12345678 5.0 1000");
        assertTrue(actual);
    }

    @Test
    void cd_missing_apr_is_invalid() {
        boolean actual = createValidator.validate("Create CD  12345678");
        assertFalse(actual);
    }

    @Test
    void cd_missing_initial_amount_is_invalid() {
        boolean actual = createValidator.validate("Create CD 12345678 5.0");
        assertFalse(actual);
    }

    @Test
    void cd_extra_parameters_is_invalid() {
        boolean actual = createValidator.validate("Create CD 12345678 5.0 1000 extraParam");
        assertFalse(actual);
    }

    @Test
    void cd_mixed_case_with_valid_parameters_is_valid() {
        boolean actual = createValidator.validate("CrEaTe Cd 12345678 5.0 1000 ");
        assertTrue(actual);
    }

    @Test
    void cd_all_lowercase_with_valid_parameters_is_valid() {
        boolean actual = createValidator.validate("create cd 12345678 5.0 1000");
        assertTrue(actual);
    }

    @Test
    void create_savings_with_invalid_parameters() {
        boolean actual = createValidator.validate("Create savings 123abc45 10");
        assertFalse(actual);
    }

    @Test
    void create_account_with_too_many_numbers() {
        boolean actual = createValidator.validate("Create Savings 1123458909876543321 10.0");
        assertFalse(actual);
    }

    @Test
    void create_account_with_apr_exceeding_ten() {
        boolean actual = createValidator.validate("Create checking 12345678 0 11.0");
        assertFalse(actual);
    }

}
