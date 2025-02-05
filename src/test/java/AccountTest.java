import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {
    public static final double APR = 10.0;
    public static final int MONEY_AMOUNT = 20;
    public static final String QUICK_ID = "12345678";
    //all banks require an 8-digit ID, an APR value from 0-10(decimals included)
    Account checkings; //reference for any calls to an account


    @BeforeEach
    public void setUp() {
        checkings = new Checkings(QUICK_ID);
    }

    @Test
    public void checking_account_starts_empty() {
        double actual = checkings.getBalance(); //since savings and checkings start the same, we'll test the one case

        assertEquals(0, actual);
    }

    @Test
    public void checking_account_with_specific_APR() {
        checkings.setAPR(APR);
        double actual = checkings.getAPR(); //check is the apr on the account is correct

        assertEquals(APR,actual);
    }

    @Test
    public void deposit_20_dollars_to_account() {
        checkings.deposit(MONEY_AMOUNT); //add 20 to balance
        double actual = checkings.getBalance();

        assertEquals(MONEY_AMOUNT, actual);
    }

    @Test
    public void deposit_multiple_times() {
        checkings.deposit(MONEY_AMOUNT);
        checkings.deposit(10);
        double actual = checkings.getBalance();

        assertEquals(30, actual);
    }

    @Test
    public void withdraw_10_dollars_from_account() {
        checkings.deposit(MONEY_AMOUNT);
        checkings.withdraw(10);//take 10 from account
        double actual = checkings.getBalance();

        assertEquals(10, actual);
    }

    @Test
    public void withdraw_multiple_times() {
        checkings.deposit(MONEY_AMOUNT);
        checkings.withdraw(10);
        checkings.withdraw(11);//withdrawing past the amount to ensure the withdrawal still operates properly
        double actual = checkings.getBalance();

        assertEquals(0,actual);
    }

    @Test
    public void cant_withdraw_past_zero() {//if withdrawal exceeds the balance, then withdraw the max amount(?)
        checkings.withdraw(MONEY_AMOUNT);
        double actual = checkings.getBalance();

        assertEquals(0,actual);

    }
}
