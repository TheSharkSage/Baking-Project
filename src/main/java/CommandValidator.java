public class CommandValidator {

    private final Bank bank;

    public CommandValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {
        if (command == null) {
            return false;
        }
        String[] parts = command.split(" ");

        // Validate command format


        if (parts.length < 3) {
            return false;
        }

        String bankCommand = parts[0];//store the initally given commands
        String accountType = parts[1];
        // Extract the account ID from the command
        String accountId = parts[2];

        //check for bounds
        if (!accountId.matches("\\d{8}")) {
            return false;
        }

        //check for duplicate
        if (bank.accountExistsByID(accountId)) {
            return false;
        }

        if (!isValidAccount(accountType)) {
            return false;
        }

        //validate the existence of account
        if (accountType.equalsIgnoreCase("CD")) {
            return validateCDParameters(parts);
        }

        return parts.length == 3;//parameter check for non cd accounts
    }

    private boolean validateCDParameters(String[] parts) {
        //CD accounts require 5 inputs: Create CD APR accountID APR amount
        if (parts.length != 5) {
            return false;
        }

        try {
            //read the APR field as a double
            double apr = Double.parseDouble(parts[3]);
            if (apr < 0) {
                return false;
            }

            //check initial amount
            double amount = Double.parseDouble(parts[4]);
            if (amount <= 0) {
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public boolean isValidCommand(String bankCommand) {//compare the pattern
        return bankCommand.equalsIgnoreCase("create") ||
                bankCommand.equalsIgnoreCase("withdraw") ||
                bankCommand.equalsIgnoreCase("deposit");
    }

    public boolean isValidAccount(String accountType) {
        return accountType.equalsIgnoreCase("Checkings") ||
                accountType.equalsIgnoreCase("Savings") ||
                accountType.equalsIgnoreCase("CD");
    }
}