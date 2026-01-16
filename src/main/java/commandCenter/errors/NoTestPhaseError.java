package commandCenter.errors;

public class NoTestPhaseError extends CommandError {
    private final String message;

    public NoTestPhaseError() {
        message = "Tickets can only be reported during testing phases.";
    }

    public String getMessage() {
        return message;
    }
}
