package banking;

public class CommandProcessor {
    private Bank bank;

    // TODO: store the logged commands within a list 

    public CommandProcessor(Bank bank) {
        this.bank = bank;
    }

    public void process(String command) {
        //check command type
        String[] parts = command.split(" ");
        String commandType = parts[0].toLowerCase();

        //test each command with their own method
        switch(commandType) {
            case "create":
                processCreateCommand(parts);
                break;
            case "deposit":
                processDepositCommand(parts);
                break;
            case "withdraw":
                processWithdrawCommand(parts);
                break;
            case "transfer":
                processTransferCommand(parts);
                break;
            case "pass":
                processPassCommand(parts);
                break;
            default:
                System.out.println("Invalid Command Passed to process");
                return;
        }

        }

    private void processPassCommand(String[] parts) {
        int months = Integer.parseInt(parts[1]);
        bank.passTime(months);
    }


    private void processCreateCommand(String[] parts) {
        //read teh create command and then execute it using the bank classes
        //sk for account, ask fork account, then process command
        //[create, accType, accId, ]
        String type = parts[1].toLowerCase();
        int id = Integer.parseInt(parts[2]);
        double apr = Double.parseDouble(parts[3]);

        Account account = null;
        
        switch(type) {
            case "checking":
                account = new Checking(id, apr);
                break;
            case "savings": 
                account = new Savings(id, apr);
                break;
            case "cd":
                double startAmount = Double.parseDouble(parts[4]);
                int currentMonth = bank.getCurrentMonth();
                account = new CD(id, apr, startAmount, currentMonth);
                break;
            default:
                //output when command syntax is valid but account type doesn't exist
                throw new IllegalArgumentException("Invalid banking.Account Type");
        }

        bank.addAccount(account);
    }

    private void processDepositCommand(String[] parts) {
        //[deposit, accountId, amount]
        int id = Integer.parseInt(parts[1]);
        double amount = Double.parseDouble(parts[2]);

        Account account = bank.findAccount(id);

        if(account == null) {
            System.out.println("banking.Account not found");
            //return false;
        }
        
        bank.deposit(id, amount);
    }

    private void processWithdrawCommand(String[] parts) {
        //[deposit, accountId, amount]
        int id = Integer.parseInt(parts[1]);
        double amount = Double.parseDouble(parts[2]);

        Account account = bank.findAccount(id);

        if(account == null) {
            System.out.println("banking.Account not found");
            //return false;
        }
        
        bank.withdraw(id, amount);
    }

    private void processTransferCommand(String[] parts) {
        int fromAccount = Integer.parseInt(parts[1]);
        int toAccount = Integer.parseInt(parts[2]);
        double amount = Double.parseDouble(parts[3]);

        bank.transfer(fromAccount, toAccount, amount);

    }

}
