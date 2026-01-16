package factories.ticketFactories;

import java.util.List;
import fileio.TicketInput;
import tickets.Ticket;

public abstract class TicketFactory {
    private int currId;

    public void setCurrId(final int currId) {
        this.currId = currId;
    }

    public void addTicket(final List<Ticket> ticketList, final TicketInput ticketInput,
                          final String timestamp) {
        Ticket currTicket = createTicket(ticketInput, currId, timestamp);
        ticketList.add(currTicket);
    }

    protected abstract Ticket createTicket(TicketInput ticketInput, int id, String timestamp);

    public abstract boolean canCreateAnonymous();
}
