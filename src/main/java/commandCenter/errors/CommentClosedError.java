package commandCenter.errors;

public class CommentClosedError extends CommandError {
    private final String message;

    public CommentClosedError() {
        message = "Reporters cannot comment on CLOSED tickets.";
    }

    public String getMessage() {
        return message;
    }
}
