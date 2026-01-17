package commandCenter.receivers;

import java.util.List;
import tickets.Ticket;

public class CommentRemover {
    private final List<Ticket> ticketList;

    public CommentRemover(final List<Ticket> ticketList) {
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

    public void removeComment(final String username, final Ticket ticket) {
        Ticket.Comment currComment = null;
        for (Ticket.Comment comment : ticket.getComments()) {
            if (comment.getAuthor().equals(username)) {
                currComment = comment;
            }
        }
        if (currComment != null) {
            ticket.getComments().remove(currComment);
        }
    }
}
