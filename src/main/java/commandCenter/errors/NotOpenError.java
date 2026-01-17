package commandCenter.errors;

public class NotOpenError extends CommandError {
    private final String message;

    public NotOpenError() {
        message = "Only OPEN tickets can be assigned.";
    }

    public String getMessage() {
        return message;
    }
}
