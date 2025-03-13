package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OutputGenerator {
    private Bank bank;
    private CommandStorage commandStorage;

    public OutputGenerator(Bank bank, CommandStorage commandStorage) {
        this.bank = bank;
        this.commandStorage = commandStorage;
    }

    public List<String> generateOutput() {
        List<String> output = new ArrayList<>();

        // First, add all account states and their transaction histories
        Map<Integer, List<String>> accountTransactions = new LinkedHashMap<>();

        // Process valid commands to determine which affected which accounts
        for (String command : commandStorage.getValidCommands()) {
            String[] parts = command.split(" ");
            String commandType = parts[0].toLowerCase();

            if (commandType.equals("pass")) {
                continue; // Skip pass commands in history
            }

            if (commandType.equals("create")) {
                int accountId = Integer.parseInt(parts[2]);
                if (!accountTransactions.containsKey(accountId)) {
                    accountTransactions.put(accountId, new ArrayList<>());
                }
                // We don't add create commands to transaction history
            } else if (commandType.equals("deposit") || commandType.equals("withdraw")) {
                int accountId = Integer.parseInt(parts[1]);
                if (bank.accountExistsByID(accountId)) {
                    accountTransactions.computeIfAbsent(accountId, k -> new ArrayList<>()).add(command);
                }
            } else if (commandType.equals("transfer")) {
                int fromAccountId = Integer.parseInt(parts[1]);
                int toAccountId = Integer.parseInt(parts[2]);

                if (bank.accountExistsByID(fromAccountId)) {
                    accountTransactions.computeIfAbsent(fromAccountId, k -> new ArrayList<>()).add(command);
                }

                if (bank.accountExistsByID(toAccountId)) {
                    accountTransactions.computeIfAbsent(toAccountId, k -> new ArrayList<>()).add(command);
                }
            }
        }

        // Add account states and transactions for all existing accounts
        for (Map.Entry<Integer, Account> entry : bank.getAccounts().entrySet()) {
            int accountId = entry.getKey();
            Account account = entry.getValue();

            // Add account state
            output.add(formatAccountState(account));

            // Add transactions that affected this account
            List<String> transactions = accountTransactions.getOrDefault(accountId, new ArrayList<>());
            output.addAll(transactions);
        }

        // Add invalid commands
        output.addAll(commandStorage.getInvalidCommands());

        return output;
    }

    private String formatAccountState(Account account) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);

        String accountType;
        if (account instanceof Checking) {
            accountType = "Checking";
        } else if (account instanceof Savings) {
            accountType = "Savings";
        } else if (account instanceof CD) {
            accountType = "Cd";
        } else {
            accountType = "Unknown";
        }

        return String.format("%s %d %s %s",
                accountType,
                account.getAccountId(),
                decimalFormat.format(account.getBalance()),
                decimalFormat.format(account.getAPR()));
    }
}
