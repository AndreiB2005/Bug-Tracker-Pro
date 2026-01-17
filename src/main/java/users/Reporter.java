package users;

import java.util.Comparator;
import java.util.List;
import fileio.UserInput;
import tickets.Ticket;
import commandCenter.errors.CommentWrongReporterError;

public class Reporter extends User {
    public Reporter(final UserInput userInput) {
        super(userInput);
    }

    public void checkComment(final Ticket ticket) throws CommentWrongReporterError {
        if (!getUsername().equals(ticket.getReportedBy())) {
            throw new CommentWrongReporterError(getUsername(), ticket.getId());
        }
    }
}
