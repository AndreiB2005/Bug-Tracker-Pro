package users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;
import java.time.LocalDate;
import fileio.UserInput;
import fileio.SearchInput;
import expertise.ExpertiseArea;
import tickets.Ticket;
import commandCenter.errors.CommentNotAssignedError;
import visitors.Visitor;

public class Developer extends User implements SearchMember {
    private final LocalDate hireDate;
    private final ExpertiseArea expertiseArea;
    private final Seniority seniority;
    private final List<Ticket> assignedTickets = new ArrayList<>();
    private double performanceScore = 0.0;
    private final List<String> notifications = new ArrayList<>();

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
        hireDate = LocalDate.parse(userInput.getHireDate());
        expertiseArea = ExpertiseArea.valueOf(userInput.getExpertiseArea());
        seniority = Seniority.valueOf(userInput.getSeniority());
    }

    public ObjectNode createOutput(final ObjectMapper mapper) {
        ObjectNode developerNode = mapper.createObjectNode();
        developerNode.put("username", getUsername());
        developerNode.put("expertiseArea", getExpertiseArea());
        developerNode.put("seniority", getSeniority());
        developerNode.put("performanceScore", 0.0);
        developerNode.put("hireDate", hireDate.toString());
        return developerNode;
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

    public double getPerformanceScore() {
        return performanceScore;
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

    public boolean canAssignTicket(final Ticket ticket) {
        List<String> expertiseList = getExpertiseList();
        List<String> priorityList = getPriorities();
        List<String> typeList = getTicketTypes();
        return expertiseList.contains(ticket.getExpertiseArea())
                && priorityList.contains(ticket.getBusinessPriority())
                && typeList.contains(ticket.getType());
    }

    public <T> List<T> acceptList(final Visitor<T> visitor, final SearchInput input,
                                   final List<T> list) {
        return visitor.returnFilteredList(this, input, list);
    }

    public String getMemberName() {
        return getUsername();
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void update(final String notification) {
        notifications.add(notification);
    }
}
