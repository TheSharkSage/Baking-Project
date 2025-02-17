public class CommandValidator {

    private final Bank bank;

    public CommandValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {
        if (command == null) {
            return false;
        }
        //split string into parts and check each one
        String[] parts = command.split(" ");

        // Validate length of command format
        if (parts.length < 3) {
            return false;
        }


        String bankCommand = parts[0].toLowerCase();//store the initally given commands
//        String accountType = parts[1];
//        // Extract the account ID from the command
//        String accountId = parts[2];


        // Validate based on command type
        switch (bankCommand) {
            case "create":
                return validateCreateCommand(parts);
            case "deposit":
            case "withdraw":
                return validateTransactionCommand(parts);
            case "getapr":
                return validateGetAprCommand(parts);
            default:
                return false;
        }
    }

    private boolean validateGetAprCommand(String[] parts) {
        if (parts.length != 2) {
            return false;
        }
        return bank.accountExistsByID(parts[1]);
    }

    private boolean validateCreateCommand(String[] parts) {
        // Basic length check for create command
        if (parts.length < 3) {
            return false;
        }

        String accountType = parts[1].toLowerCase();
        String accountId = parts[2];

        // Validate account ID format (8 digits)
        if (!accountId.matches("\\d{8}")) {
            return false;
        }

        // Check for duplicate account
        if (bank.accountExistsByID(accountId)) {
            return false;
        }

        // Special handling for CD accounts
        if (accountType.equals("cd")) {
            return validateCDParameters(parts);
        }

        // For regular accounts (Savings/Checkings)
        return parts.length == 3 && isValidAccount(accountType);
    }

    private boolean validateTransactionCommand(String[] parts) {
        //command parts: action accoundID amount
        if (parts.length != 3) {
            return false;
        }

        String accountId = parts[1];
        String amount = parts[2];

        if (!bank.accountExistsByID(accountId)) {
            return false;
        }

        //check for positive number and include decimals
        try {
            double value = Double.parseDouble(amount);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
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
                accountType.equalsIgnoreCase("CD") ||
                accountType.equalsIgnoreCase("getapr");
    }
}