package commandCenter.errors;

import milestones.Milestone;

public class TicketInMilestoneError extends CommandError {
    private final String message;

    public TicketInMilestoneError(final String milestoneName, final int ticketId) {
        message = "Tickets " + ticketId + " already assigned to milestone " + milestoneName + ".";
    }

    public String getMessage() {
        return message;
    }
}
