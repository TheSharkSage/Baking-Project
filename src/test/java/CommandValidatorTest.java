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


}
