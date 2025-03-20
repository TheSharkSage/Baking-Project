package banking;

public class PassTimeValidator extends CommandValidator {
    public PassTimeValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validateSpecific(String[] command) {
        String commandType = command[0].toLowerCase();
        int time = Integer.parseInt(command[1]);

        if(!commandType.equals("pass")) {
            return false;
        }

        return isValidTime(time);

    }

    private boolean isValidTime(int time) {
        return time >= 1 && time <=60;
    }
}
