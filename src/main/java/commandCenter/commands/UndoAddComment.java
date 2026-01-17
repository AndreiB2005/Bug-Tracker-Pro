package commandCenter.commands;

import fileio.CommandInput;
import tickets.Ticket;
import commandCenter.receivers.CommentRemover;
import commandCenter.Output;
import commandCenter.errors.CommentAnonymousError;

public class UndoAddComment extends Command {
    private final CommentRemover commentRemover;
    private final int ticketId;

    public UndoAddComment(final CommandInput input, final CommentRemover commentRemover) {
        super(input);
        this.commentRemover = commentRemover;
        ticketId = input.getTicketID();
    }

    public Output execute() throws CommentAnonymousError {
        Ticket ticket = commentRemover.getTicket(ticketId);
        if (ticket != null) {
            if (ticket.getReportedBy().isEmpty()) {
                throw new CommentAnonymousError();
            }
            commentRemover.removeComment(getUsername(), ticket);
        }
        return null;
    }
}
