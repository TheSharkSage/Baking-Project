package banking;

import java.util.List;
import java.util.ArrayList;

public class CommandStorage {
    private List<String> validCommands;
    private List<String> invalidCommands;

    public CommandStorage() {
        validCommands = new ArrayList<>();
        invalidCommands = new ArrayList<>();
    }

    public void addValidCommand(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Invalid command cannot be null");
        }
        validCommands.add(s);
    }

    public List<String> getValidCommands() {
        return validCommands;
    }

    public void addInvalidCommand(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Invalid command cannot be null");
        }
        invalidCommands.add(s);
    }

    public List<String> getInvalidCommands() {
        return invalidCommands;
    }

}
