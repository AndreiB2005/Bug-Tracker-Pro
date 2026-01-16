package commandCenter.commands;

import main.AppBrain;
import fileio.CommandInput;
import commandCenter.Output;
import users.User;

public class LostInvestors extends Command {
    private final AppBrain brain;

    public LostInvestors(final CommandInput input, final AppBrain brain) {
        super(input);
        this.brain = brain;
    }

    public Output execute() {
        brain.stopApp();
        return null;
    }
}
