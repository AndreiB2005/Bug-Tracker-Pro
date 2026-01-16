package factories.ticketFactories;

import fileio.TicketInput;
import tickets.Ticket;
import tickets.Bug;

public class BugFactory extends TicketFactory {
    protected Ticket createTicket(final TicketInput ticketInput, final int id,
                                  final String timestamp) {
        return new Bug(ticketInput, id, timestamp);
    }

    public boolean canCreateAnonymous() {
        return true;
    }
}
