package visitors;

import java.util.List;
import java.time.LocalDate;
import fileio.SearchInput;
import users.Developer;
import users.Manager;
import tickets.Ticket;
import strategies.AssigneeTicketViewStrategy;
import strategies.FullTicketViewStrategy;

public class TicketSearch implements Visitor<Ticket> {
    public List<Ticket> returnFilteredList(final Developer developer, final SearchInput input,
                                           final List<Ticket> list) {
        developer.setViewStrategy(new AssigneeTicketViewStrategy());
        LocalDate dateCreated = (input.getCreatedAt() != null) ?
                LocalDate.parse(input.getCreatedAt()) : null;
        LocalDate before = (input.getCreatedBefore() != null) ?
                LocalDate.parse(input.getCreatedBefore()) : null;
        LocalDate after = (input.getCreatedAfter() != null) ?
                LocalDate.parse(input.getCreatedAfter()) : null;
        List<Ticket> ticketList = developer.getViewStrategy()
                .viewTickets(developer, list);
        return filterList(input, ticketList).stream()
                .filter(ticket -> !input.isAvailableForAssignment()
                        || developer.canAssignTicket(ticket))
                .toList();
    }

    public List<Ticket> returnFilteredList(final Manager manager, final SearchInput input,
                                           final List<Ticket> list) {
        manager.setViewStrategy(new FullTicketViewStrategy());
        List<Ticket> ticketList = manager.getViewStrategy()
                .viewTickets(manager, list);
        ticketList = filterList(input, ticketList);
        List<String> keywords = input.getKeywords();
        return ticketList.stream()
                .filter(ticket -> keywords == null || keywords.isEmpty()
                        || keywords.stream().anyMatch(ticket.getTitle()::contains)
                        || ((ticket.getDescription() != null && !ticket.getDescription().isEmpty())
                                && keywords.stream().anyMatch(ticket.getDescription()::contains)))
                .toList();
    }

    private List<Ticket> filterList(final SearchInput input, final List<Ticket> ticketList) {
        LocalDate dateCreated = (input.getCreatedAt() != null) ?
                LocalDate.parse(input.getCreatedAt()) : null;
        LocalDate before = (input.getCreatedBefore() != null) ?
                LocalDate.parse(input.getCreatedBefore()) : null;
        LocalDate after = (input.getCreatedAfter() != null) ?
                LocalDate.parse(input.getCreatedAfter()) : null;
        return ticketList.stream()
                .filter(ticket -> input.getBusinessPriority() == null
                        || input.getBusinessPriority().equals(ticket.getBusinessPriority()))
                .filter(ticket -> input.getType() == null
                        || input.getType().equals(ticket.getType()))
                .filter(ticket -> dateCreated == null
                        || ticket.wasCreatedAt().equals(dateCreated))
                .filter(ticket -> before == null
                        || ticket.wasCreatedAt().isBefore(before))
                .filter(ticket -> after == null
                        || ticket.wasCreatedAt().isAfter(after))
                .toList();
    }
}
