package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CDTest{
    public static final double INIT_BALANCE = 170.56;
    public static final int QUICK_ID = 12345678;
    public static final double APR = 10.0;
    public static final int CURENT_MONTH = 0;
    Account cd;

    @BeforeEach
    public void setUp(){
        cd = new CD(QUICK_ID, INIT_BALANCE, APR, CURENT_MONTH);
    }

    @Test
    public void create_CD_account_with_specific_amount() {
        double actual = cd.getBalance();
        assertEquals(INIT_BALANCE,actual);

    }

    @Test
    public void cannot_receive_deposit() {
        cd.deposit(10);
        double actual = cd.getBalance();
        assertEquals(170.56, actual);
    }

}
