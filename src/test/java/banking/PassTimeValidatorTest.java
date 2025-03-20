package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PassTimeValidatorTest {
    public CommandValidator passTimeValidator;

    public Bank bank;

    @BeforeEach
    void setUp() {
        //not needed? just checking the strings
        bank = new Bank();

        passTimeValidator = new CommandValidator(bank);
    }

    @Test
    void valid_time_pass() {
        boolean actual = passTimeValidator.validate("Pass 1");
        assertTrue(actual);
    }
}
