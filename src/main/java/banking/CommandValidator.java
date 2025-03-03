package banking;

public class CommandValidator {
    protected Bank bank;

    //will defer all inputs to the childrne based on the first command
    //recommended to use a switch case

    //constructor
    public CommandValidator(Bank bank) {
        this.bank = bank;
    }

    //template for validation
    public boolean validateSpecific(String[] command) {
        return false;
    }


    public int getMinimumPartsRequired() {
        return 3;
    }


    // public boolean validateGetAprCommand(String[] parts) {
    //     if (parts.length != 2) {
    //         return false;
    //     }
    //     return bank.accountExistsByID(parts[1]);
    // }


   public boolean isValidAmount(String amount) {
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

        //check the first field and delegate to child class
        switch(commandType) {
            case "create":
                //instantiate a child to delegate command
                CreateValidator create= new CreateValidator(bank);
                return create.validateSpecific(parts);
            case "deposit":
                DepositValidator deposit = new DepositValidator(bank);
                return deposit.validateSpecific(parts);
            default:
                //output when command syntax is valid but  type doesn't exist
                //throw new IllegalArgumentException("Invalid Command Type");
                return false;
        }
    }



}