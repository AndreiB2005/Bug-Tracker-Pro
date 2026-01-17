package commandCenter.receivers;

import java.util.List;
import java.util.Comparator;
import users.Developer;
import tickets.Ticket;

public class AssignedTicketPrinter {
    public List<Ticket> printTicketList(final Developer currDeveloper) {
        return currDeveloper.getAssignedTickets().stream()
                .sorted(Comparator.comparing(Ticket::getPriorityLevel).reversed()
                        .thenComparing(Ticket::getCreatedAt)
                        .thenComparing(Ticket::getId)
                )
                .toList();
    }
}
