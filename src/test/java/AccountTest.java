import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
//test to see if changes are updated to the right branch

public class AccountTest {
    public static final double APR = 11.0;
    public static final int MONEY_AMOUNT = 20;
    public static final String QUICK_ID = "12345678";
    //all banks require an 8-digit ID, an APR value from 0-10(decimals included)
    private Account checking; //reference for any calls to an account


    @BeforeEach
    public void setUp() {
        checking = new Checking(QUICK_ID);
    }

    @Test
    public void checking_account_starts_empty() {
        double actual = checking.getBalance(); //since savings and checking start the same, we'll test the one case

        assertEquals(0, actual);
    }

    @Test
    public void checking_account_with_specific_APR() {
        checking.setAPR(APR);
        double actual = checking.getAPR(); //check is the apr on the account is correct

        assertEquals(APR,actual);
    }

    @Test
    public void deposit_20_dollars_to_account() {
        checking.deposit(MONEY_AMOUNT); //add 20 to balance
        double actual = checking.getBalance();

        assertEquals(MONEY_AMOUNT, actual);
    }

    @Test
    public void deposit_multiple_times() {
        checking.deposit(MONEY_AMOUNT);
        checking.deposit(10);
        double actual = checking.getBalance();

        assertEquals(30, actual);
    }

    @Test
    public void withdraw_10_dollars_from_account() {
        checking.deposit(MONEY_AMOUNT);
        checking.withdraw(10);//take 10 from account
        double actual = checking.getBalance();

        assertEquals(10, actual);
    }

    @Test
    public void withdraw_multiple_times() {
        checking.deposit(MONEY_AMOUNT);
        checking.withdraw(10);
        checking.withdraw(11);//withdrawing past the amount to ensure the withdrawal still operates properly
        double actual = checking.getBalance();

        assertEquals(0,actual);
    }

    @Test
    public void cant_withdraw_past_zero() {//if withdrawal exceeds the balance, then withdraw the max amount(?)
        checking.withdraw(MONEY_AMOUNT);
        double actual = checking.getBalance();

        assertEquals(0,actual);

    }
}
