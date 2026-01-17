package commandCenter.errors;

public class WrongMilestoneError extends CommandError {
    private final String message;

    public WrongMilestoneError(final String username, final String milestoneName) {
        message = "Developer " + username + " is not assigned to milestone "
                + milestoneName + ".";
    }

    public String getMessage() {
        return message;
    }
}
