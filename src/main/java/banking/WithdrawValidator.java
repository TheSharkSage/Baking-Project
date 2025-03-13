package banking;

public class WithdrawValidator extends CommandValidator{
    private TimeService timeService;
    public WithdrawValidator(Bank bank) {
        super(bank);
        this.bank = bank;
        //dependency injection for time passing
        this.timeService = new BankTimeService();
    }

    public WithdrawValidator(Bank bank, TimeService timeService) {
        super(bank);
        this.bank = bank;
        this.timeService = timeService;
    }

    @Override
    public boolean validateSpecific(String[] command) {
        //store the specific command types
        //[deposit, accId, amount]
        String commandType = command[0].toLowerCase();
        String accIdStr = command[1];
        String amount = command[2];

        if (!commandType.equals("withdraw")) {
            return false;
        }

        if(bank.getAccounts().isEmpty()) {
            return false;
        }

        //run validation methods
        if (!isValidAccountID(accIdStr)) {
            System.out.println("Invalid account ID");
            return false;
        }
        int accId = Integer.parseInt(accIdStr);

        //check for duplicates
        if(!bank.accountExistsByID(accId)) {
            System.out.println("banking.Account ID does not exist");
            return false;
        }

        if (!super.isValidAmount(amount)) {
            System.out.println("Invalid amount");
            return false;
        }

        // command validation for CD meeting passTime criteria
        Account account = bank.findAccount(accId);
        if (account instanceof CD) {
            CD cdAccount = (CD) account;
            int currentMonth = bank.getCurrentMonth();
            if(currentMonth - cdAccount.getCreationMonth() < 12) {
                System.out.println("canot withdraw from CD before 12 months");
                return false;
            }
        }

        // time pass and withdrawal validation for savings
        if (account instanceof Savings) {
            //todo logic handling for one withdraw per month
            Savings savingsAccount = (Savings) account;
            double withdrawAmount = Double.parseDouble(amount);
            if (withdrawAmount > 2500) {
                System.out.println("Cannot withdraw more than 2500 at a time");
                return false;
            }
        }

        if (account instanceof Checking) {
            //todo logic handling for one withdraw per month
            Checking checkingsAccount = (Checking) account;
            double withdrawAmount = Double.parseDouble(amount);
            if (withdrawAmount > 400) {
                System.out.println("Cannot withdraw more than 400 at a time");
                return false;
            }
        }



        return true;
    }

    //Withdraw validation helpers

    public boolean isValidAccountID(String accountId) {
        //validate the proper id length
        return accountId != null && accountId.matches("\\d{8}");
    }
}
