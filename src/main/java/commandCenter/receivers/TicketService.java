package commandCenter.receivers;

import java.util.List;
import java.time.LocalDate;
import users.Developer;
import tickets.Ticket;
import history.TicketEvent;
import milestones.Milestone;

public class TicketService {
    private final List<Developer> developers;

    public TicketService(final List<Developer> developers) {
        this.developers = developers;
    }

    public Developer getDeveloper(final String username) {
        for (Developer currDeveloper : developers) {
            if (currDeveloper.getUsername().equals(username)) {
                return currDeveloper;
            }
        }
        return null;
    }

    public Ticket getTicket(final Developer developer, final int ticketId) {
        for (Ticket currTicket : developer.getAssignedTickets()) {
            if (currTicket.getId() == ticketId) {
                return currTicket;
            }
        }
        return null;
    }

    public void setLastClosed(final Developer developer, final Ticket ticket) {
        for (Milestone milestone : developer.getUserMilestones()) {
            if (milestone.getTickets().contains(ticket) && ticket.getStatus().equals("CLOSED")) {
                milestone.setLastTicketResolved(ticket.getId());
                break;
            }
        }
    }

    public void upgradeTicket(final Ticket ticket) {
        ticket.upgradeStatus();
    }

    public void downgradeTicket(final Ticket ticket) {
        ticket.downgradeStatus();
    }

    public void addEvent(final Developer developer, final Ticket ticket,
                         final LocalDate timestamp, final String oldStatus) {
        TicketEvent event = new TicketEvent.TicketEventBuilder(
                "STATUS_CHANGED", developer.getUsername(), timestamp)
                .changeStatus(oldStatus, ticket.getStatus())
                .build();
        developer.getHistoryMap().get(ticket).add(event);
    }
}
