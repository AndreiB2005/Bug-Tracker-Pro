package commandCenter.commands;

import java.util.ArrayList;
import java.util.Map;
import fileio.CommandInput;
import tickets.Ticket;
import history.TicketEvent;
import commandCenter.receivers.TicketHistoryPrinter;
import commandCenter.Output;
import users.Developer;
public class ViewTicketHistory extends Command {
    private final TicketHistoryPrinter historyPrinter;

    public ViewTicketHistory(final CommandInput input,
                             final TicketHistoryPrinter historyPrinter) {
        super(input);
        this.historyPrinter = historyPrinter;
    }

    public Output execute() {
        Map<Ticket, ArrayList<TicketEvent>> historyMap =
                historyPrinter.printHistory(getCurrUser());
        return new Output.OutputBuilder(this)
                .assignTicketHistory(historyMap)
                .build();
    }
}
