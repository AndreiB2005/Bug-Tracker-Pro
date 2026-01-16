package commandCenter.commands;

import java.util.List;
import fileio.CommandInput;
import fileio.TicketInput;
import tickets.Ticket;
import factories.ticketFactories.TicketFactory;
import commandCenter.Output;
import commandCenter.errors.CommandError;
import commandCenter.errors.NoTestPhaseError;
import commandCenter.errors.ReportAnonymousError;

public class ReportTicket extends Command {
    private final TicketFactory ticketFactory;
    private final List<Ticket> ticketList;
    private final TicketInput ticketInput;
    private final String createdAt;
    private final boolean isTestPhase;

    public ReportTicket(final CommandInput input, final TicketFactory ticketFactory,
                        final List<Ticket> ticketList, final boolean isTestPhase) {
        super(input);
        ticketInput = input.getParams();
        this.ticketFactory = ticketFactory;
        this.ticketList = ticketList;
        createdAt = input.getTimestamp();
        this.isTestPhase = isTestPhase;
    }

    public Output execute() throws CommandError {
        if (!isTestPhase) {
            throw new NoTestPhaseError();
        }
        if (ticketInput.getReportedBy().isEmpty() && !ticketFactory.canCreateAnonymous()) {
            throw new ReportAnonymousError();
        }
        ticketFactory.addTicket(ticketList, ticketInput, createdAt);
        if (ticketInput.getReportedBy().isEmpty()) {
            ticketList.getLast().setBusinessPriority("LOW");
        }
        return null;
    }
}
