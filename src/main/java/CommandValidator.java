public class CommandValidator {

    private final Bank bank;

    public CommandValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {
        String[] parts = command.split(" ");
        // Validate command format
        if (parts.length != 3) {
            return false;
        }

        // Extract the account ID from the command
        String accountId = parts[2];

        // Check if account already exists
        return !bank.accountExistsByID(accountId);
    }
}