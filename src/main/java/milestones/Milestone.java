package milestones;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Getter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ArrayList;
import main.AppBrain;
import fileio.CommandInput;
import users.Developer;
import users.Manager;
import tickets.Ticket;

@Getter
public class Milestone {
    private final Manager managerMilestone;
    private final String name;
    private final List<Milestone> blockingFor = new ArrayList<>();
    private final LocalDate dueDate;
    private final LocalDate createdAt;
    private final List<Ticket> tickets;
    private final List<Developer> assignedDevs;
    private final List<Milestone> blockedBy = new ArrayList<>();
    private int timesUpdated = 0;

    public Milestone(final CommandInput commandInput, final AppBrain brain) {
        managerMilestone = getManager(brain.getManagers(), commandInput.getUsername());
        name = commandInput.getName();
        dueDate = LocalDate.parse(commandInput.getDueDate());
        tickets = createTicketsList(commandInput.getTickets(), brain.getTickets());
        assignedDevs = createDevsList(commandInput.getAssignedDevs(), brain.getDevelopers());
        createdAt = LocalDate.parse(commandInput.getTimestamp());
    }

    public ObjectNode createOutput(final ObjectMapper mapper, final LocalDate currDate) {
        ObjectNode milestoneNode = mapper.createObjectNode();
        milestoneNode.put("name", name);
        ArrayNode blockingArray = mapper.createArrayNode();
        for (Milestone blockedMilestone : blockingFor) {
            blockingArray.add(blockedMilestone.getName());
        }
        milestoneNode.set("blockingFor", blockingArray);
        milestoneNode.put("dueDate", dueDate.toString());
        milestoneNode.put("createdAt", createdAt.toString());
        ArrayNode ticketsId = mapper.createArrayNode();
        for (Ticket currTicket : tickets) {
            ticketsId.add(currTicket.getId());
        }
        milestoneNode.set("tickets", ticketsId);
        ArrayNode devsNames = mapper.createArrayNode();
        for (Developer currDev : assignedDevs) {
            devsNames.add(currDev.getUsername());
        }
        milestoneNode.set("assignedDevs", devsNames);
        milestoneNode.put("createdBy", managerMilestone.getUsername());
        milestoneNode.put("status", getStatus());
        milestoneNode.put("isBlocked", !blockedBy.isEmpty());
        milestoneNode.put("daysUntilDue", getDaysUntilDue(currDate));
        milestoneNode.put("overdueBy", getOverdue(currDate));
        ArrayNode openTickets = mapper.createArrayNode();
        for (Integer currId : getOpenTickets()) {
            openTickets.add(currId);
        }
        milestoneNode.set("openTickets", openTickets);
        ArrayNode closedTickets = mapper.createArrayNode();
        for (Integer currId : getClosedTickets()) {
            closedTickets.add(currId);
        }
        milestoneNode.set("closedTickets", closedTickets);
        milestoneNode.put("completionPercentage", getCompletionPercentage());
        ArrayNode repartition = mapper.createArrayNode();
        for (Developer currDev : assignedDevs) {
            ObjectNode devStats = mapper.createObjectNode();
            devStats.put("developer", currDev.getUsername());
            ArrayNode assignedTickets = mapper.createArrayNode();
            for (Ticket currTicket : currDev.getAssignedTickets()) {
                assignedTickets.add(currTicket.getId());
            }
            devStats.set("assignedTickets", assignedTickets);
            repartition.add(devStats);
        }
        milestoneNode.set("repartition", repartition);
        return milestoneNode;
    }

    public void updateTickets(final LocalDate timestamp) {
        if (blockedBy.isEmpty()) {
            int updatesNeeded = (int) ChronoUnit.DAYS.between(createdAt, timestamp) / 3;
            for (Ticket currTicket : tickets) {
                if (ChronoUnit.DAYS.between(timestamp, dueDate) <= 1) {
                    currTicket.setBusinessPriority("CRITICAL");
                } else {
                    for (int cnt = timesUpdated; cnt < updatesNeeded; cnt++) {
                        currTicket.setNextPriorityLevel();
                    }
                }
            }
            timesUpdated = updatesNeeded;
        }
    }

    public String getStatus() {
        for (Ticket currTicket : tickets) {
            if (!currTicket.getStatus().equals("CLOSED")) {
                return "ACTIVE";
            }
        }
        return "COMPLETED";
    }

    public int getDaysUntilDue(final LocalDate currDate) {
        int daysUntilDue = (int) ChronoUnit.DAYS.between(currDate, dueDate) + 1;
        return Math.max(0, daysUntilDue);
    }

    public int getOverdue(final LocalDate currDate) {
        int daysOverdue = (int) ChronoUnit.DAYS.between(dueDate, currDate) + 1;
        return Math.max(0, daysOverdue);
    }

    public List<Integer> getOpenTickets() {
        return tickets.stream()
                .filter(ticket -> !"CLOSED".equals(ticket.getStatus()))
                .map(Ticket::getId)
                .toList();
    }

    public List<Integer> getClosedTickets() {
        return tickets.stream()
                .filter(ticket -> "CLOSED".equals(ticket.getStatus()))
                .map(Ticket::getId)
                .toList();
    }

    public double getCompletionPercentage() {
        int completedTickets = 0;
        for (Ticket currTicket : tickets) {
            if (currTicket.getStatus().equals("CLOSED")) {
                completedTickets++;
            }
        }
        double percentage = (double) completedTickets / tickets.size();
        return Math.min(1.0, Math.round(percentage * 100.0) / 100.0);
    }

    private Manager getManager(final List<Manager> allManagers, final String username) {
        for (Manager currManager : allManagers) {
            if (username.equals(currManager.getUsername())) {
                return currManager;
            }
        }
        return null;
    }

    private List<Developer> createDevsList(final List<String> assignedDevs,
                                      final List<Developer> allDevelopers) {
        return assignedDevs.stream()
                .map(devName -> allDevelopers.stream()
                        .filter(developer -> devName.equals(developer.getUsername()))
                        .findFirst()
                        .orElse(null)
                )
                .toList();
    }

    private List<Ticket> createTicketsList(final List<Integer> ticketsList,
                                           final List<Ticket> allTickets) {
        return allTickets.stream()
                .filter(ticket -> ticketsList.contains(ticket.getId()))
                .toList();
    }
}
