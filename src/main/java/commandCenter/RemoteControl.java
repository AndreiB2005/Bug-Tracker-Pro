package commandCenter;

import commandCenter.commands.Command;
import commandCenter.errors.CommandError;

public class RemoteControl {
    private Command command;

    public void setCommand(final Command command) {
        this.command = command;
    }

    public Output executeCommand() throws CommandError {
        return command.execute();
    }
}
