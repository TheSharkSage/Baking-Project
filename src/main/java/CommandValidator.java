public class CommandValidator {
    protected Bank bank;

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

        // check if empty or exceeding string length and return result
        if (parts == null || parts.length < getMinimumPartsRequired()) {
            return false;
        }
        return validateSpecific(parts);
    }

    public boolean isValidAccountID(String accountId) {
        //validate the proper id length
        return accountId != null && accountId.matches("\\d{8}");
    }


}