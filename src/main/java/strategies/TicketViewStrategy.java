package strategies;

import java.util.List;
import users.User;
import tickets.Ticket;

public interface TicketViewStrategy {
    List<Ticket> viewTickets(User user, List<Ticket> ticketList);
}
