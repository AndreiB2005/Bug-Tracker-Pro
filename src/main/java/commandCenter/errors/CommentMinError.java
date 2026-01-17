package commandCenter.errors;

public class CommentMinError extends CommandError {
    private final String message;

    public CommentMinError() {
        message = "Comment must be at least 10 characters long.";
    }

    public String getMessage() {
        return message;
    }
}
