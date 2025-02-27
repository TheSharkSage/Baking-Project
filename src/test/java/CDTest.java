import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CDTest{
    public static final double INIT_BALANCE = 170.56;
    public static final int QUICK_ID = 12345678;
    Account cd;

    @BeforeEach
    public void setUp(){
        cd = new CD(QUICK_ID, INIT_BALANCE);
    }

    @Test
    public void create_CD_account_with_specific_amount() {
        double actual = cd.getBalance();
        assertEquals(INIT_BALANCE,actual);

    }
}
