package commandCenter.errors;

public class ReportAnonymousError extends CommandError {
    private final String message;

    public ReportAnonymousError() {
        message = "Anonymous reports are only allowed for tickets of type BUG.";
    }

    public String getMessage() {
        return message;
    }
}
