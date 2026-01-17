package commandCenter.commands;

import java.util.List;
import fileio.CommandInput;
import users.Developer;
import tickets.Ticket;
import commandCenter.receivers.AssignedTicketPrinter;
import commandCenter.Output;
import commandCenter.errors.CommandError;

public class ViewAssignedTickets extends Command {
    private final AssignedTicketPrinter ticketPrinter;
    private final Developer developer;

    public ViewAssignedTickets(final CommandInput input,
                               final AssignedTicketPrinter ticketPrinter,
                               final Developer developer) {
        super(input);
        this.ticketPrinter = ticketPrinter;
        this.developer = developer;
    }

    public Output execute() throws CommandError {
        List<Ticket> ticketList = ticketPrinter.printTicketList(developer);
        return new Output.OutputBuilder(this)
                .assignDeveloperTicketsList(ticketList)
                .build();
    }
}
