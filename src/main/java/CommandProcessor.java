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

    private void processDepositCommand(String[] parts) {

    }

    private void processCreateCommand(String[] commandType) {
        //read teh create command and then execute it using the bank classes
        //sk for account, ask fork account, then process command
        //bank.account
        // todo: implement

    }

}
