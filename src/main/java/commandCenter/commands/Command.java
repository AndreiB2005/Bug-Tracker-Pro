package commandCenter.commands;

import lombok.Getter;
import java.time.LocalDate;
import fileio.CommandInput;
import commandCenter.Output;
import commandCenter.errors.CommandError;
import users.User;

@Getter
public abstract class Command {
    private final String command;
    private final String username;
    private final LocalDate timestamp;
    private User currUser = null;

    public Command(final CommandInput commandInput) {
        command = commandInput.getCommand();
        username = commandInput.getUsername();
        timestamp = LocalDate.parse(commandInput.getTimestamp());
    }

    public void setCurrUser(final User currUser) {
        this.currUser = currUser;
    }

    public abstract Output execute() throws CommandError;
}
