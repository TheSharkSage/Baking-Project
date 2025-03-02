import java.util.List;
import java.util.ArrayList;

public class CommandStorage {
    private List<String> invalidCommands;

    public CommandStorage() {
        invalidCommands = new ArrayList<>();
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
