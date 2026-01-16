package commandCenter.receivers;

import java.util.List;
import users.User;
import tickets.Ticket;

public class TicketPrinter {
    private final List<Ticket> ticketList;

    public TicketPrinter(final List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public List<Ticket> printTicketList(final User currUser) {
        return currUser.getUserTickets(ticketList);
    }
}
