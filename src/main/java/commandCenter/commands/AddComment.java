package commandCenter.commands;

import java.time.LocalDate;
import java.util.Map;
import fileio.CommandInput;
import tickets.Ticket;
import commandCenter.receivers.CommentGenerator;
import commandCenter.Output;
import commandCenter.errors.CommandError;
import commandCenter.errors.CommentAnonymousError;
import commandCenter.errors.CommentClosedError;
import commandCenter.errors.CommentMinError;

public class AddComment extends Command {
    private static final int MIN_CHARACTERS = 10;

    private final CommentGenerator generator;
    private final int ticketId;
    private final String comment;
    private final LocalDate currDate;

    private static final Map<String, Boolean> closedMap = Map.of(
            "REPORTER", false,
            "DEVELOPER", true
    );

    public AddComment(final CommandInput input, final CommentGenerator generator) {
        super(input);
        this.generator = generator;
        ticketId = input.getTicketID();
        comment = input.getComment();
        currDate = LocalDate.parse(input.getTimestamp());
    }

    public Output execute() throws CommandError {
        Ticket currTicket = generator.getTicket(ticketId);
        if (currTicket != null) {
            if (currTicket.getReportedBy().isEmpty()) {
                throw new CommentAnonymousError();
            }
            if (currTicket.getStatus().equals("CLOSED")
                    && !closedMap.get(getCurrUser().getRole())) {
                throw new CommentClosedError();
            }
            if (comment.length() < MIN_CHARACTERS) {
                throw new CommentMinError();
            }
            getCurrUser().checkComment(currTicket);
            generator.addComment(comment, getUsername(), currTicket, currDate);
        }
        return null;
    }
}
