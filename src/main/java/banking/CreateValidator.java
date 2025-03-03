package banking;

public class CreateValidator extends CommandValidator {

    public CreateValidator(Bank bank) {
        super(bank);
        //this.bank = bank;
    }
    //override methods

    //commented out until other transaction validators are implemented
//    @Override
//    public int getMinimumPartsRequired() {
//        return 3;
//    }

    @Override
    public boolean validateSpecific(String[] command) {
        //store teh specific account types
        String commandType = command[0].toLowerCase();
        String accType = command[1].toLowerCase();
        String accIdStr = command[2];

        //logic to allocate for apr commands
        if(command.length == 4) {
            String apr = command[3];
            if (!isValidApr(apr)) {
                return false;
            }
        }

        //check for the specific command type
        if (!commandType.equals("create")) {
            return false;
        }

        //run validation methods
        if (!isValidAccountID(accIdStr)) {
            System.out.println("Invalid account ID");
            return false;
        }
        int accId = Integer.parseInt(accIdStr);


        if(bank.accountExistsByID(accId)) {
            System.out.println("banking.Account ID already exists");
            return false;
        }

        //check for cd due to unique nature
        if (accType.equals("cd") && !validateCDParameters(command)){
            System.out.println("invalid banking.CD parameters");
            return false;
        }

        if(!isValidAccountType(accType)) {
            System.out.println("Invalid account type");
            return false;
        }
        
        return true;
    }

    //validation helpers

    public boolean isValidAccountType(String accType) {
        return accType.equalsIgnoreCase("banking.Checking") ||
                accType.equalsIgnoreCase("banking.Savings") ||
                accType.equalsIgnoreCase("banking.CD");
    }

    


    public boolean validateCDParameters(String[] command) {
        if (command.length != 5) { // create cd [accountId] [APR] [amount]
            return false;
        }

        try {
            //convert the apr from string to double
            double apr = Double.parseDouble(command[3]);
            if (apr < 0) {
                return false;
            }
            //convert amount into string
            double amount = Double.parseDouble(command[4]);
            return amount > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public boolean isValidApr(String APR) {
    //parse the string into double and check if 0
        try {
            double apr = Double.parseDouble(APR);
            if (apr < 0) {
                return false;
            }
            //convert amount into string and check if valid APR amount
            double amount = Double.parseDouble(APR);
            return (10 >= amount && amount > 0);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValidAccountID(String accountId) {
        //validate the proper id length
        return accountId != null && accountId.matches("\\d{8}");
    }



}
