package commandCenter.errors;

public class CommentNotAssignedError extends CommandError {
    private final String message;

    public CommentNotAssignedError(final String username, final int ticketId) {
        message = "Ticket " + ticketId + " is not assigned to the developer " + username + ".";
    }

    public String getMessage() {
        return message;
    }
}
