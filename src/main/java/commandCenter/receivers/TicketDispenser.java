package commandCenter.receivers;

import java.util.List;
import main.AppBrain;
import users.Developer;
import tickets.Ticket;
import milestones.Milestone;

public class TicketDispenser {
    private final AppBrain brain;

    public TicketDispenser(final AppBrain brain) {
        this.brain = brain;
    }

    public Ticket getTicket(final int ticketId) {
        for (Ticket currTicket : brain.getTickets()) {
            if (currTicket.getId() == ticketId) {
                return currTicket;
            }
        }
        return null;
    }

    public Milestone getMilestone(final Ticket ticket) {
        for (Milestone currMilestone : brain.getMilestones()) {
            if (currMilestone.getTickets().contains(ticket)) {
                return currMilestone;
            }
        }
        return null;
    }

    public void assignTicket(final Developer developer, final Ticket ticket) {
        developer.getAssignedTickets().add(ticket);
        ticket.setStatus("IN_PROGRESS");
        ticket.setAssignedAt(brain.getCurrDate());
        ticket.setAssignedTo(developer.getUsername());
    }
}
