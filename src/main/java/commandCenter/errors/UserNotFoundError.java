package commandCenter.errors;

public class UserNotFoundError extends CommandError {
    private final String message;

    public UserNotFoundError(final String username) {
        message = "The user " + username + " does not exist.";
    }

    public String getMessage() {
        return message;
    }
}