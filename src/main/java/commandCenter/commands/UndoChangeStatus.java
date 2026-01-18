package commandCenter.commands;

import fileio.CommandInput;
import users.Developer;
import tickets.Ticket;
import commandCenter.receivers.TicketService;
import commandCenter.Output;
import commandCenter.errors.TicketNotAssignedError;

public class UndoChangeStatus extends Command {
    private final TicketService service;
    private final int ticketId;

    public UndoChangeStatus(final CommandInput input, final TicketService service) {
        super(input);
        this.service = service;
        ticketId = input.getTicketID();
    }

    public Output execute() throws TicketNotAssignedError {
        Developer developer = service.getDeveloper(getUsername());
        Ticket ticket = service.getTicket(developer, ticketId);
        if (ticket == null) {
            throw new TicketNotAssignedError(developer.getUsername(), ticketId);
        }
        String oldStatus = ticket.getStatus();
        service.downgradeTicket(ticket);
        if (!ticket.getStatus().equals(oldStatus)) {
            service.addEvent(developer, ticket, getTimestamp(), oldStatus);
        }
        return null;
    }
}
