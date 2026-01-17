package commandCenter.errors;

public class CommentAnonymousError extends CommandError {
    private final String message;

    public CommentAnonymousError() {
        message = "Comments are not allowed on anonymous tickets.";
    }

    public String getMessage() {
        return message;
    }
}
