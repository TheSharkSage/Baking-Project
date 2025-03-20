package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;

public class OutputGenerator {
    private Bank bank;
    private CommandStorage commandStorage;

    public OutputGenerator(Bank bank, CommandStorage commandStorage) {
        this.bank = bank;
        this.commandStorage = commandStorage;
    }

    public List<String> generateOutput() {
        List<String> output = new ArrayList<>();

        // Create a map to track which commands affected which accounts, preserving order
        Map<Integer, List<String>> accountTransactions = new LinkedHashMap<>();

        // Process all valid commands to identify which ones affected each account
        for (String command : commandStorage.getValidCommands()) {
            String[] parts = command.split(" ");
            String commandType = parts[0].toLowerCase();

            // Skip "pass" commands
            if (commandType.equals("pass")) {
                continue;
            }

            try {
                if (commandType.equals("deposit") || commandType.equals("withdraw")) {
                    int accountId = Integer.parseInt(parts[1]);
                    addTransactionToAccount(accountTransactions, accountId, command);
                } else if (commandType.equals("transfer")) {
                    int fromId = Integer.parseInt(parts[1]);
                    int toId = Integer.parseInt(parts[2]);

                    addTransactionToAccount(accountTransactions, fromId, command);
                    addTransactionToAccount(accountTransactions, toId, command);
                }
                // Create commands are not included in transaction history

            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                // Skip commands with parsing errors
                continue;
            }
        }

        // Use a queue to implement FIFO for account processing
        Queue<String> outputQueue = new LinkedList<>();

        // Process each account - first output its state, then all its transactions
        for (Account account : bank.getAccounts().values()) {
            int accountId = account.getAccountId();

            // Add the account state to the queue
            outputQueue.offer(formatAccountState(account));

            // Add all transactions for this account in chronological order
            List<String> transactions = accountTransactions.getOrDefault(accountId, new ArrayList<>());
            for (String transaction : transactions) {
                outputQueue.offer(transaction);
            }
        }

        // Process the queue in FIFO order and add to output
        while (!outputQueue.isEmpty()) {
            output.add(outputQueue.poll());
        }

        // Add all invalid commands in the order they were processed
        output.addAll(commandStorage.getInvalidCommands());

        return output;
    }

    private void addTransactionToAccount(Map<Integer, List<String>> accountTransactions, int accountId, String command) {
        // Only track transactions for accounts that exist
        if (bank.accountExistsByID(accountId)) {
            if (!accountTransactions.containsKey(accountId)) {
                accountTransactions.put(accountId, new ArrayList<>());
            }
            accountTransactions.get(accountId).add(command);
        }
    }

    private String formatAccountState(Account account) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);

        // Determine account type with proper capitalization
        String accountType;
        if (account instanceof Checking) {
            accountType = "Checking";
        } else if (account instanceof Savings) {
            accountType = "Savings";
        } else if (account instanceof CD) {
            accountType = "Cd";  // Note the specific capitalization for CD
        } else {
            accountType = "Unknown";
        }

        // Format account ID
        int id = account.getAccountId();

        // Format balance with truncated decimal (not rounded)
        String formattedBalance = decimalFormat.format(account.getBalance());

        // Format APR with truncated decimal (not rounded)
        String formattedAPR = decimalFormat.format(account.getAPR());

        // Return formatted account state
        return String.format("%s %d %s %s", accountType, id, formattedBalance, formattedAPR);
    }
}