package banking;

public class DepositValidator extends  CommandValidator{

    public DepositValidator(Bank bank) {
        super(bank);
        this.bank = bank;
    }

    //Override methods

    @Override
    public boolean validateSpecific(String[] command) {
        //store the specific command types
        //[deposit, accId, amount]
        String commandType = command[0].toLowerCase();
        String accIdStr = command[1];
        String amount = command[2];

        if(bank.getAccounts().isEmpty()) {
            return false;
        }

        //check for the specific command type


        if (!commandType.equals("deposit")) {
            return false;
        }

//        //run validation methods
//        if (!isValidAccountID(accIdStr)) {
//            System.out.println("Invalid account ID");
//            return false;
//        }
        int accId = Integer.parseInt(accIdStr);

        if(!bank.accountExistsByID(accId)) {
            System.out.println("banking.Account ID doesn't exist");
            return false;
        }


        if (!super.isValidAmount(amount)) {
            System.out.println("Invalid amount");
            return false;
        }


        Account account = bank.findAccount(accId);
        double depositAmount = Double.parseDouble(amount);

        if (account instanceof Checking && depositAmount > 400) {
            System.out.println("Deposit amount exceeds 1000 for checking account");
            return false;
        }

        return account instanceof Savings && depositAmount > 2500;

    }

    //Deposit validation helpers

    public boolean isValidAccountID(String accountId) {
        //validate the proper id length
        return accountId != null && accountId.matches("\\d{8}");
    }


}
