package commandCenter.errors;

public class BlockedMilestoneError extends CommandError {
    private final String message;

    public BlockedMilestoneError(final int ticketId, final String milestoneName) {
        message = "Cannot assign ticket " + ticketId + " from blocked milestone "
                + milestoneName + ".";
    }

    public String getMessage() {
        return message;
    }
}
