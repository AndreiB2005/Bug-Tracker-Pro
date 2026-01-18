package commandCenter.commands;

import fileio.CommandInput;
import users.Developer;
import tickets.Ticket;
import commandCenter.receivers.TicketRemover;
import commandCenter.Output;
import commandCenter.errors.NotInProgressError;

public class UndoAssignTicket extends Command {
    private final TicketRemover ticketRemover;
    private final Developer developer;
    private final int ticketId;

    public UndoAssignTicket(final CommandInput input, final TicketRemover ticketRemover,
                            final Developer developer) {
        super(input);
        this.ticketRemover = ticketRemover;
        this.developer = developer;
        ticketId = input.getTicketID();
    }

    public Output execute() throws NotInProgressError {
        Ticket currTicket = ticketRemover.getTicket(developer, ticketId);
        if (!currTicket.getStatus().equals("IN_PROGRESS")) {
            throw new NotInProgressError();
        }
        ticketRemover.removeTicket(developer, currTicket, getTimestamp());
        return null;
    }
}
