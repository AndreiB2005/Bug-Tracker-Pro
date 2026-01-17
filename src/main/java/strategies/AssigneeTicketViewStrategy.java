package strategies;

import java.util.List;
import java.util.Comparator;
import users.User;
import tickets.Ticket;

public class AssigneeTicketViewStrategy implements TicketViewStrategy {
    public List<Ticket> viewTickets(final User user, final List<Ticket> ticketList) {
        return user.getUserMilestones().stream()
                .flatMap(milestone -> milestone.getTickets().stream())
                .filter(ticket -> "OPEN".equals(ticket.getStatus()))
                .sorted(
                        Comparator.comparing(Ticket::getCreatedAt)
                                .thenComparing(Ticket::getId)
                )
                .toList();
    }
}
