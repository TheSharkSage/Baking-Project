public class DepositValidator extends  CommandValidator{
    Bank bank = new Bank();

    public DepositValidator(Bank bank) {
        super(bank);
        this.bank = bank;
    }

    //Override methods

    //commented out until other transaction validators are implemented
//    @Override
//    public int getMinimumPartsRequired() {
//        return 3;
//    }

    @Override
    public boolean validateSpecific(String[] command) {
        //store the specific command types
        //[deposit, accId, amount]
        String commandType = command[0].toLowerCase();
        String accIdStr = command[1];
        String amount = command[2];


        //check for the specific command type


        if (!commandType.equals("deposit")) {
            return false;
        }

        //run validation methods
        if (!isValidAccountID(accIdStr)) {
            System.out.println("Invalid account ID");
            return false;
        }
        int accId = Integer.parseInt(accIdStr);

        if(bank.accountExistsByID(accId)) {
            System.out.println("Account ID already exists");
            return false;
        }

        if (!super.isValidAmount(amount)) {
            System.out.println("Invalid amount");
            return false;
        }

        return true;
    }

    //Deposit validation helpers

    public boolean isValidAccountID(String accountId) {
        //validate the proper id length
        return accountId != null && accountId.matches("\\d{8}");
    }

}
