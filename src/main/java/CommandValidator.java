public class CommandValidator {

    private final Bank bank;

    public CommandValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {
        String[] parts = command.split(" ");
        String bankCommand = parts[0];//store the initally given commands
        String accountType = parts[1];
        // Extract the account ID from the command
        String accountId = parts[2];
        // Validate command format
        if (parts.length != 3) {
            return false;
        }

        if (!accountId.matches("\\d{8}")) {
            return false;
        }


        // Check for any errors in command validation fields
        return (!bank.accountExistsByID(accountId)) &&
                (isValidAccount(accountType)) &&
                (isValidCommand(bankCommand));
    }

    public boolean isValidCommand(String bankCommand) {//compare the pattern
        return bankCommand.equalsIgnoreCase("Checkings") ||
                bankCommand.equalsIgnoreCase("Savings") ||
                bankCommand.equalsIgnoreCase("CD");
    }

    public boolean isValidAccount(String accountType) {
        return accountType.equalsIgnoreCase("Checkings") ||
                accountType.equalsIgnoreCase("Savings") ||
                accountType.equalsIgnoreCase("CD");
    }
}