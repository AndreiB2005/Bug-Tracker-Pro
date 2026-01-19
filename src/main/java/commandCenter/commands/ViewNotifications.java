package commandCenter.commands;

import java.util.List;
import fileio.CommandInput;
import users.Developer;
import commandCenter.receivers.NotificationHandler;
import commandCenter.Output;

public class ViewNotifications extends Command {
    private final NotificationHandler handler;

    public ViewNotifications(final CommandInput input, final NotificationHandler handler) {
        super(input);
        this.handler = handler;
    }

    public Output execute() {
        Developer developer = handler.getDeveloper(getUsername());
        List<String> notifications = handler.showNotifications(developer);
        return new Output.OutputBuilder(this)
                .assignNotifications(notifications)
                .build();
    }
}
