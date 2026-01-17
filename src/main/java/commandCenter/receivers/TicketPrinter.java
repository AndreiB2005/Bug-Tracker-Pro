package commandCenter.receivers;

import java.util.List;
import java.util.Map;
import users.User;
import tickets.Ticket;
import strategies.*;

public class TicketPrinter {
    private final List<Ticket> ticketList;

    private static final Map<String, TicketViewStrategy> strategyMap = Map.of(
            "REPORTER", new CreatorTicketViewStrategy(),
            "DEVELOPER", new AssigneeTicketViewStrategy(),
            "MANAGER", new FullTicketViewStrategy()
    );

    public TicketPrinter(final List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public List<Ticket> printTicketList(final User currUser) {
        currUser.setStrategy(strategyMap.get(currUser.getRole()));
        return currUser.getStrategy().viewTickets(currUser, ticketList);
    }
}
