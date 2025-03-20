package banking;

import java.util.List;

public class MasterControl {
    private Bank bank;
    private CommandValidator commandValidator;
    private CommandProcessor commandProcessor;
    private CommandStorage commandStorage;

    //reference the bank

    //constructor
    public MasterControl(CommandValidator commandValidator,
                         CommandProcessor commandProcessor,
                         CommandStorage commandStorage) {
        this.commandValidator = commandValidator;
        this.commandProcessor = commandProcessor;
        this.commandStorage = commandStorage;

        this.bank = commandValidator.getBank();
    }

    public List<String> start(List<String> input) {
        for(String command : input) {
            // Check if it's a pass command (we'll process it but not store it)
            boolean isPassCommand = command.toLowerCase().startsWith("pass ");

            // Process only valid commands
            if(commandValidator.validate(command)) {
                commandProcessor.process(command);

                // Only store non-pass commands
                if (!isPassCommand) {
                    commandStorage.addValidCommand(command);
                }
            }
            else {
                commandStorage.addInvalidCommand(command);
            }
        }

        // Generate output
        OutputGenerator outputGenerator = new OutputGenerator(bank, commandStorage);
        return outputGenerator.generateOutput();
    }

}
