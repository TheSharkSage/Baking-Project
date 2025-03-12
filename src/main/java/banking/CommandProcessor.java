package banking;

public class CommandProcessor {
    private Bank bank;


    public CommandProcessor(Bank bank) {
        this.bank = bank;
    }

    public void process(String command) {
        //check command type
        String[] parts = command.split(" ");
        String commandType = parts[0];

        //test each command with their own method
        switch(commandType) {
            case "create":
                processCreateCommand(parts);
                break;
            case "deposit":
                processDepositCommand(parts);
                break;
//            case "withdraw":
//                processWithdrawCommand(parts);
//                break;
//            case "getapr":
//                processGetAprCommand(parts);
//                break;
            default:
                System.out.println("Invalid Command Passed to process");
                return;
        }

        }

    private boolean processDepositCommand(String[] parts) {
        //[deposit, accoutnId, amount]
        int id = Integer.parseInt(parts[1]);
        double amount = Double.parseDouble(parts[2]);

        Account account = bank.findAccount(id);

        if(account == null) {
            System.out.println("banking.Account not found");
            return false;
        }
        
        return account.deposit(amount);
    }

    private void processCreateCommand(String[] parts) {
        //read teh create command and then execute it using the bank classes
        //sk for account, ask fork account, then process command
        //[create, accType, accId, ]
        String type = parts[1];
        int id = Integer.parseInt(parts[2]);
        double apr = Double.parseDouble(parts[3]);
        if (parts.length == 5) {
            // establish token for start amount when included with CD command
            double startAmount = Double.parseDouble(parts[4]);
        }


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
                account = new CD(id, apr, startAmount);
                break;
            default:
                //output when command syntax is valid but account type doesn't exist
                throw new IllegalArgumentException("Invalid banking.Account Type");
        }

        bank.addAccount(account);
    }

}
