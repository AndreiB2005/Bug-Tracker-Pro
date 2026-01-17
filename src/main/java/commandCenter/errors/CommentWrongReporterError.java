package commandCenter.errors;

public class CommentWrongReporterError extends CommandError {
    private final String message;

    public CommentWrongReporterError(final String username, final int ticketId) {
        message = "Reporter " + username + " cannot comment on ticket " + ticketId + ".";
    }

    public String getMessage() {
        return message;
    }
}
