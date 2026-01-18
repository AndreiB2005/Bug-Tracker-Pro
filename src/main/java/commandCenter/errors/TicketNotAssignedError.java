package commandCenter.errors;

public class TicketNotAssignedError extends CommandError {
    private final String message;

    public TicketNotAssignedError(final String username, final int ticketId) {
        message = "Ticket " + ticketId + " is not assigned to developer " + username + ".";
    }

    public String getMessage() {
        return message;
    }
}
