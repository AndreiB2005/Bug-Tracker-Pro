package users;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;
import fileio.UserInput;
import expertise.ExpertiseArea;
import tickets.Ticket;
import commandCenter.errors.CommentNotAssignedError;

public class Developer extends User {
    private final String hireDate;
    private final ExpertiseArea expertiseArea;
    private final Seniority seniority;
    private final List<Ticket> assignedTickets = new ArrayList<>();

    private enum Seniority {
        JUNIOR(
                List.of("LOW", "MEDIUM"),
                List.of("BUG", "UI_FEEDBACK")
        ),
        MID(
                List.of("LOW", "MEDIUM", "HIGH"),
                List.of("BUG", "UI_FEEDBACK", "FEATURE_REQUEST")
        ),
        SENIOR(
                List.of("LOW", "MEDIUM", "HIGH", "CRITICAL"),
                List.of("BUG", "UI_FEEDBACK", "FEATURE_REQUEST")
        );

        private final List<String> priorities;
        private final List<String> ticketTypes;

        Seniority(List<String> priorities, List<String> issueTypes) {
            this.priorities = priorities;
            this.ticketTypes = issueTypes;
        }

        public List<String> getPriorities() {
            return priorities.stream()
                    .sorted()
                    .toList();
        }

        public List<String> getTicketTypes() {
            return ticketTypes.stream()
                    .sorted()
                    .toList();
        }
    }

    public Developer(final UserInput userInput) {
        super(userInput);
        hireDate = userInput.getHireDate();
        expertiseArea = ExpertiseArea.valueOf(userInput.getExpertiseArea());
        seniority = Seniority.valueOf(userInput.getSeniority());
    }

    public List<Ticket> getAssignedTickets() {
        return assignedTickets;
    }

    public String getExpertiseArea() {
        return expertiseArea.name();
    }

    public String getSeniority() {
        return seniority.name();
    }

    public List<String> getExpertiseList() {
        return expertiseArea.getExpertiseList();
    }

    public List<String> getPriorities() {
        return seniority.getPriorities();
    }

    public List<String> getTicketTypes() {
        return seniority.getTicketTypes();
    }

    public static List<String> getRequiredExpertise(String expertise) {
        return Stream.of(ExpertiseArea.values())
                .filter(area -> area.getExpertiseList().contains(expertise))
                .map(Enum::name)
                .sorted()
                .toList();
    }

    public static List<String> getRequiredSeniority(String priority, String ticketType) {
        return Arrays.stream(Seniority.values())
                .filter(seniority ->
                        seniority.priorities.contains(priority)
                                && seniority.ticketTypes.contains(ticketType)
                )
                .map(Enum::name)
                .sorted()
                .toList();
    }

    public void checkComment(final Ticket ticket) throws CommentNotAssignedError {
        if (!assignedTickets.contains(ticket)) {
            throw new CommentNotAssignedError(getUsername(), ticket.getId());
        }
    }

    public List<Ticket> getUserTickets() {
        return getHistoryMap().keySet().stream()
                .sorted(
                        Comparator.comparing(Ticket::getCreatedAt)
                                .thenComparing(Ticket::getId)
                )
                .toList();
    }
}
