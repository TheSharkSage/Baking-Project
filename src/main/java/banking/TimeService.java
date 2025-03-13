package banking;

public interface TimeService {
    //create a framework for the bank to track the accounts while using a time service
        int getCurrentMonth();
        void advanceMonth(int months);
        boolean isEligibleForWithdrawal(int accountCreationMonth, int accountType);
    }
