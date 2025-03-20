package banking;

public class BankTimeService implements TimeService {
    private int currentMonth;

    // used constants to specify different account types
    public static final int ACCOUNT_TYPE_CHECKING = 1;
    public static final int ACCOUNT_TYPE_SAVINGS = 2;
    public static final int ACCOUNT_TYPE_CD = 3;

    //constructor for banking-related time functionality
    public BankTimeService() {
        this.currentMonth = 0;
    }

    @Override
    public int getCurrentMonth() {
        return currentMonth;
    }

    @Override
    public void advanceMonth(int months) {
        if(months < 1 || months > 60) {
            throw new IllegalArgumentException("Months must be between 1 and 60");
        }
        currentMonth += months;

    }

    @Override
    public boolean isEligibleForWithdrawal(int accountCreationMonth, int accountType) {
        if(accountType == ACCOUNT_TYPE_CD) {
            return currentMonth - accountCreationMonth < 12;
        }
        //todo savings have a max of one withdrawal per month
        return true;
    }
}
