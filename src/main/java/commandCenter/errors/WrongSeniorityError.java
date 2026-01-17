package commandCenter.errors;

import java.util.List;
import java.util.Iterator;
import users.Developer;

public class WrongSeniorityError extends CommandError {
    private StringBuilder message = null;
    private final List<String> requiredSeniority;
    private final Developer developer;
    private final int ticketId;

    public WrongSeniorityError(final List<String> requiredSeniority,
                                   final Developer developer, final int ticketId) {
        this.requiredSeniority = requiredSeniority;
        this.developer = developer;
        this.ticketId = ticketId;
    }

    public String getMessage() {
        message = new StringBuilder("Developer ")
                .append(developer.getUsername())
                .append(" cannot assign ticket ")
                .append(ticketId)
                .append(" due to seniority level. Required: ");
        Iterator<String> iterator = requiredSeniority.iterator();
        while (iterator.hasNext()) {
            String seniority = iterator.next();
            message.append(seniority);
            if (iterator.hasNext()) {
                message.append(", ");
            } else {
                message.append("; ");
            }
        }
        message.append("Current: ")
                .append(developer.getSeniority())
                .append(".");
        return message.toString();
    }
}
