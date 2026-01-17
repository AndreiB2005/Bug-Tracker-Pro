package commandCenter.errors;

public class NotInProgressError extends CommandError {
    private final String message;

    public NotInProgressError() {
        message = "Only IN_PROGRESS tickets can be unassigned.";
    }

    public String getMessage() {
        return message;
    }
}
