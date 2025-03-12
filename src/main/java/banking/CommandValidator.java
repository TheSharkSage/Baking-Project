package banking;

public class CommandValidator {
    protected Bank bank;

    //constructor
    public CommandValidator(Bank bank) {
        this.bank = bank;
    }

    public static CommandValidator getValidator(String commandType, Bank bank) {
        //check the first field and delegate to child class
        switch(commandType) {
            case "create":
                return new CreateValidator(bank);
            case "deposit":
                return new DepositValidator(bank);
            case "transfer":
                return new TransferValidator(bank);
            case "withdraw":
                return new WithdrawValidator(bank);
            case "pass":
                return new PassTimeValidator(bank);
            default:
                //output when command syntax is valid but type doesn't exist
                return new CommandValidator(bank);
        }
    }

    //template for validation
    public boolean validateSpecific(String[] command) {
        return false;
    }


    public int getMinimumPartsRequired() {
        return 2;
    }


    // public boolean validateGetAprCommand(String[] parts) {
    //     if (parts.length != 2) {
    //         return false;
    //     }
    //     return bank.accountExistsByID(parts[1]);
    // }


    protected boolean isValidAmount(String amount) {
        //convert string amount to a double
        try {
            double value = Double.parseDouble(amount);//parse the double to convert every value
            return value > 0;
        } catch (NumberFormatException e){
            return false;
        }
    }

    //store the string into a list and access each element
    public boolean validate(String command) {
        //check for null
        if(command == null) return false;
        //split the string first
        String[] parts = command.split(" ");
        String commandType = (parts[0].toLowerCase());

        // check if empty or exceeding string length and return result
        if (parts == null || parts.length < getMinimumPartsRequired()) {
            return false;
        }

        //check for excessive whitespace
        for (String part : parts) {
            if (part.equals("")) {
                return false;
            }
        }

        CommandValidator validator = getValidator(commandType, bank);
        return validator.validateSpecific(parts);

    }



}