package factories.ticketFactories;

import fileio.TicketInput;
import tickets.Ticket;
import tickets.UiFeedback;

public class UiFeedbackFactory extends TicketFactory {
    protected Ticket createTicket(final TicketInput ticketInput, final int id,
                                  final String timestamp) {
        return new UiFeedback(ticketInput, id, timestamp);
    }

    public boolean canCreateAnonymous() {
        return false;
    }
}
