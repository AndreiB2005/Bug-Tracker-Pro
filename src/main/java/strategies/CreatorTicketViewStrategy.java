package strategies;

import java.util.List;
import java.util.Comparator;
import users.User;
import tickets.Ticket;

public class CreatorTicketViewStrategy implements TicketViewStrategy {
    public List<Ticket> viewTickets(final User user, final List<Ticket> ticketList) {
        return ticketList.stream()
                .filter(ticket -> ticket.getReportedBy().equals(user.getUsername()))
                .sorted(
                        Comparator.comparing(Ticket::getCreatedAt)
                                .thenComparing(Ticket::getId)
                )
                .toList();
    }
}
