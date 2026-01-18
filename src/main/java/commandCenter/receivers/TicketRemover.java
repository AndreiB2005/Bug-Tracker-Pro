package commandCenter.receivers;

import java.time.LocalDate;
import users.Developer;
import tickets.Ticket;
import history.TicketEvent;

public class TicketRemover {
    public Ticket getTicket(final Developer currDeveloper, final int ticketId) {
        for (Ticket currTicket : currDeveloper.getAssignedTickets()) {
            if (currTicket.getId() == ticketId) {
                return currTicket;
            }
        }
        return null;
    }

    public void removeTicket(final Developer currDeveloper, final Ticket currTicket,
                             final LocalDate timestamp) {
        currDeveloper.getAssignedTickets().remove(currTicket);
        currTicket.setStatus("OPEN");
        currTicket.setAssignedAt(null);
        currTicket.setAssignedTo(null);
        TicketEvent event = new TicketEvent.TicketEventBuilder(
                "DE-ASSIGNED", currDeveloper.getUsername(), timestamp)
                .build();
        currDeveloper.getHistoryMap().get(currTicket).add(event);
    }
}
