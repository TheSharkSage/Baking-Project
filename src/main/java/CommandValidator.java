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

        // Check if account already exists
        return !bank.accountExistsByID(accountId);
    }

    public boolean isValidCommand(String bankCommand) {//compare the pattern
        return bankCommand.equalsIgnoreCase("Checkings") ||
                bankCommand.equalsIgnoreCase("Savings") ||
                bankCommand.equalsIgnoreCase("CD");
    }

}