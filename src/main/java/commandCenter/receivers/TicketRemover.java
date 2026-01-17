package commandCenter.receivers;

import java.util.List;
import users.Developer;
import tickets.Ticket;

public class TicketRemover {
    public Ticket getTicket(final Developer currDeveloper, final int ticketId) {
        for (Ticket currTicket : currDeveloper.getAssignedTickets()) {
            if (currTicket.getId() == ticketId) {
                return currTicket;
            }
        }
        return null;
    }

    public void removeTicket(final Developer currDeveloper, final Ticket currTicket) {
        currDeveloper.getAssignedTickets().remove(currTicket);
        currTicket.setStatus("OPEN");
        currTicket.setAssignedAt(null);
        currTicket.setAssignedTo(null);
    }
}
