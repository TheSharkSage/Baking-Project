import java.util.List;

public class MasterControl {
    private CommandValidator commandValidator;
    private CommandProcessor commandProcessor;
    private CommandStorage commandStorage;

    //constructor
    public MasterControl(CommandValidator commandValidator,
                         CommandProcessor commandProcessor,
                         CommandStorage commandStorage) {
        this.commandValidator = commandValidator;
        this.commandProcessor = commandProcessor;
        this.commandStorage = commandStorage;

    }

    public List<String> start(List<String> input) {
        for(String command : input) {
            //process for validate command and store invalid command
            if((commandValidator.validate(command))) {
                commandProcessor.process(command);
            }
            else {

                commandStorage.addInvalidCommand(command);
            }

        }

        return commandStorage.getInvalidCommands();
    }

}
