package users;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import fileio.UserInput;
import tickets.Ticket;

public class Reporter extends User {
    public Reporter(final UserInput userInput) {
        super(userInput);
    }

    public List<Ticket> getUserTickets(final List<Ticket> tickets) {
        return tickets.stream()
                .filter(ticket -> ticket.getReportedBy().equals(getUsername()))
                .sorted(
                        Comparator.comparing(Ticket::getCreatedAt)
                                .thenComparing(Ticket::getId)
                )
                .toList();
    }
}
