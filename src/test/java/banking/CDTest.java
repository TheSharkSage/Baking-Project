package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CDTest{
    public static final double INIT_BALANCE = 1000;
    public static final int QUICK_ID = 12345678;
    public static final double APR = 10.0;
    public static final int CURENT_MONTH = 0;
    Account cd;

    @BeforeEach
    public void setUp(){
        cd = new CD(QUICK_ID, APR, INIT_BALANCE, CURENT_MONTH);
    }

    @Test
    public void create_CD_account_with_specific_amount() {
        double actual = cd.getBalance();
        assertEquals(INIT_BALANCE,actual);

    }

    @Test
    public void cannot_receive_deposit() {
        boolean actual = cd.deposit(10);
        assertFalse(actual);
    }

}
