package tickets;

import fileio.TicketInput;

public class UiFeedback extends Ticket {
    private final String uiElementId;
    private final BusinessValue businessValue;
    private final int usabilityScore;
    private final String screenshotUrl;
    private final String suggestedFix;

    public UiFeedback(final TicketInput ticketInput, final int id, final String timestamp) {
        super(ticketInput, id, timestamp);
        uiElementId = ticketInput.getUiElementId();
        businessValue = BusinessValue.valueOf(ticketInput.getBusinessValue());
        usabilityScore = ticketInput.getUsabilityScore();
        screenshotUrl = ticketInput.getScreenshotUrl();
        suggestedFix = ticketInput.getSuggestedFix();
    }
}
