package commandCenter.receivers;

import java.util.ArrayList;
import main.AppBrain;
import users.Developer;
import tickets.Ticket;
import milestones.Milestone;
import history.TicketEvent;

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
        TicketEvent eventAssigned = new TicketEvent.TicketEventBuilder(
                "ASSIGNED", developer.getUsername(), brain.getCurrDate())
                .build();
        TicketEvent eventChangedStatus = new TicketEvent.TicketEventBuilder(
                "STATUS_CHANGED", developer.getUsername(), brain.getCurrDate())
                .changeStatus("OPEN", "IN_PROGRESS")
                .build();
        ticket.getTicketHistory().add(eventAssigned);
        ticket.getTicketHistory().add(eventChangedStatus);
        ArrayList<TicketEvent> currEvents = new ArrayList<>(ticket.getTicketHistory());
        developer.getHistoryMap().put(ticket, currEvents);
    }
}
