package commandCenter.errors;

import java.util.List;
import java.util.Iterator;
import users.Developer;

public class WrongExpertiseAreaError extends CommandError {
    private StringBuilder message = null;
    private final List<String> requiredExpertise;
    private final Developer developer;
    private final int ticketId;

    public WrongExpertiseAreaError(final List<String> requiredExpertise,
                                   final Developer developer, final int ticketId) {
        this.requiredExpertise = requiredExpertise;
        this.developer = developer;
        this.ticketId = ticketId;
    }

    public String getMessage() {
        message = new StringBuilder("Developer ")
                .append(developer.getUsername())
                .append(" cannot assign ticket ")
                .append(ticketId)
                .append(" due to expertise area. Required: ");
        Iterator<String> iterator = requiredExpertise.iterator();
        while (iterator.hasNext()) {
            String expertise = iterator.next();
            message.append(expertise);
            if (iterator.hasNext()) {
                message.append(", ");
            } else {
                message.append("; ");
            }
        }
        message.append("Current: ")
                .append(developer.getExpertiseArea())
                .append(".");
        return message.toString();
    }
}
