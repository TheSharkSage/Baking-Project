package banking;

public class TransferValidator extends CommandValidator {

    public TransferValidator(Bank bank) {
        super(bank);
        this.bank = bank;
    }

    @Override
    public boolean validateSpecific(String[] command) {
        //store the specific command types
        //[deposit, accId, amount]
        String commandType = command[0].toLowerCase();
        String fromAccId = command[1];
        String toAccId = command[2];
        String amount = command[3];


        if(bank.getAccounts().isEmpty()) {
            return false;
        }

        //check for the specific command type


        if (!commandType.equals("transfer")) {
            return false;
        }

        //run validation methods
        if (!isValidAccountID(fromAccId) || !isValidAccountID(toAccId)) {
            System.out.println("Invalid account ID");
            return false;
        }
        int accId1 = Integer.parseInt(fromAccId);
        int accId2 = Integer.parseInt(toAccId);

        //check if account exists within the bank
        if(!bank.accountExistsByID(accId1) || !bank.accountExistsByID(accId2)) {
            System.out.println("One or both account Id's don't exist");
            return false;
        }

        //check if transfer is routed to the same account
        if(accId1 == accId2) {
            System.out.print("Error: transfer to same account");
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
        return accountId == null && accountId.matches("\\d{8}");
    }

}
