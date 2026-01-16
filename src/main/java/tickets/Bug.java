package tickets;

import fileio.TicketInput;

public class Bug extends Ticket {
    private final String expectedBehavior;
    private final String actualBehavior;
    private final Frequency frequency;
    private final Severity severity;
    private final String environment;
    private final int errorCode;

    private enum Frequency {
        RARE,
        OCCASIONAL,
        FREQUENT,
        ALWAYS
    }

    private enum Severity {
        MINOR,
        MODERATE,
        SEVERE
    }

    public Bug(final TicketInput ticketInput, final int id, final String timestamp) {
        super(ticketInput, id, timestamp);
        expectedBehavior = ticketInput.getExpectedBehavior();
        actualBehavior = ticketInput.getActualBehavior();
        frequency = Frequency.valueOf(ticketInput.getFrequency());
        severity = Severity.valueOf(ticketInput.getSeverity());
        environment = ticketInput.getEnvironment();
        errorCode = ticketInput.getErrorCode();
    }
}
