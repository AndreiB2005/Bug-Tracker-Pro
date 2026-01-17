package commandCenter.receivers;

import java.util.List;
import java.time.LocalDate;
import users.User;
import tickets.Ticket;

public class CommentGenerator {
    private final List<Ticket> ticketList;

    public CommentGenerator(final List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public Ticket getTicket(final int ticketId) {
        for (Ticket currTicket : ticketList) {
            if (currTicket.getId() == ticketId) {
                return currTicket;
            }
        }
        return null;
    }

    public void addComment(final String content,
                           final String username, final Ticket ticket, final LocalDate timestamp) {
        Ticket.Comment comment = new Ticket.Comment(username, content, timestamp);
        ticket.getComments().add(comment);
    }
}
