package commandCenter.commands;

import java.util.List;
import fileio.CommandInput;
import commandCenter.receivers.TicketPrinter;
import commandCenter.Output;
import tickets.Ticket;

public class ViewTickets extends Command {
    private final TicketPrinter ticketPrinter;

    public ViewTickets(final CommandInput input, final TicketPrinter ticketPrinter) {
        super(input);
        this.ticketPrinter = ticketPrinter;
    }

    public Output execute() {
        List<Ticket> ticketList = ticketPrinter.printTicketList(getCurrUser());
        return new Output.OutputBuilder(this)
                .assignTicketList(ticketList)
                .build();
    }
}
