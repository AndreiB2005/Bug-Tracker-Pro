package factories.ticketFactories;

import fileio.TicketInput;
import tickets.Ticket;
import tickets.FeatureRequest;

public class FeatureRequestFactory extends TicketFactory {
    protected Ticket createTicket(final TicketInput ticketInput, final int id,
                                  final String timestamp) {
        return new FeatureRequest(ticketInput, id, timestamp);
    }

    public boolean canCreateAnonymous() {
        return false;
    }
}
