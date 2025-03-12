package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CreateValidator extends CommandValidator {
    private static final double CD_MIN_BALANCE = 1000;
    private static final double CD_MAX_BALANCE = 10000;
    public CreateValidator(Bank bank) {
        super(bank);
    }
    //override methodS

    @Override
    public boolean validateSpecific(String[] command) {
        //store teh specific account types
        String commandType = command[0].toLowerCase();
        String accType = command[1].toLowerCase();
        String accIdStr = command[2];
        String aprAmount = command[3];

        //check commands from least complex to most complex

        //check for the specific command type
        if (!commandType.equals("create")) {
            return false;
        }

        if (command.length > 3) {
            System.out.println("Attempting to parse APR from: " + command[3]);
            // Parsing logic
        }
        //todo checking commands have 4 parameters [create checking, accId, apr]
        // CD commands have 5 parameters [create, cd, accId, apr, balance]

        // Before attempting to parse, check if the element exists
        if (command[3] != null) {
            try {
                double apr = Double.parseDouble(command[3]);
                // Process APR
            } catch (NumberFormatException e) {
                // Handle invalid number format
                return false;
            }
        }

        if(!isValidAccountType(accType)) {
            System.out.println("Invalid account type");
            return false;
        }

        // validate CD parameters
        if (accType.equals("cd")) {
            return validateCDParameters(command);
        }

        if (!isValidAccountID(accIdStr)) {
            System.out.println("Invalid account ID");
            return false;
        }
        int accId = Integer.parseInt(accIdStr);

        if(super.bank.accountExistsByID(accId)) {
            System.out.println("Account ID already exists");
            return false;
        }

        //run validation methods
        return isValidApr(aprAmount);
    }

    //validation helpers

    public boolean isValidAccountType(String accType) {
        return accType.equalsIgnoreCase("Checking") ||
                accType.equalsIgnoreCase("Savings") ||
                accType.equalsIgnoreCase("CD");
    }

    


    public boolean validateCDParameters(String[] command) {
        if (command.length != 5) { // create cd [accountId] [APR] [amount]
            return false;
        }

        try {
            //command[3] = new DecimalFormat("#.##").format(command[3]);
            //convert the apr from string to double
            double apr = Double.parseDouble(command[3]);

            if (apr < 0 || apr > 10.00) {
                return false;
            }
            //convert amount into string
            double balance = Double.parseDouble(command[4]);
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            decimalFormat.setRoundingMode(RoundingMode.FLOOR);

            return balance > 0;
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
