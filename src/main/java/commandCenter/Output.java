package commandCenter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Comparator;
import commandCenter.commands.Command;
import users.Developer;
import tickets.Ticket;
import milestones.Milestone;
import history.TicketEvent;

public class Output {
    private final ObjectNode objNode;

    public static class OutputBuilder {
        private static final ObjectMapper MAPPER = new ObjectMapper();
        private final ObjectNode objNode;

        public OutputBuilder(final Command command) {
            objNode = MAPPER.createObjectNode();
            objNode.put("command", command.getCommand());
            objNode.put("username", command.getUsername());
            objNode.put("timestamp", command.getTimestamp().toString());
        }

        public OutputBuilder assignError(final String error) {
            objNode.put("error", error);
            return this;
        }

        public OutputBuilder assignTicketList(final List<Ticket> ticketList) {
            ArrayNode arrayTickets = MAPPER.createArrayNode();
            for (Ticket ticket : ticketList) {
                ObjectNode ticketOutput = ticket.createOutput(MAPPER);
                ticketOutput.put("assignedAt", ticket.getAssignedAt());
                ticketOutput.put("solvedAt", ticket.getSolvedAt());
                ticketOutput.put("assignedTo", ticket.getAssignedTo());
                ticketOutput.put("reportedBy", ticket.getReportedBy());
                ticketOutput.set("comments", ticket.createCommentNode(MAPPER));
                arrayTickets.add(ticketOutput);
            }
            objNode.set("tickets", arrayTickets);
            return this;
        }

        public OutputBuilder assignMilestoneList(final List<Milestone> milestoneList,
                                                 final LocalDate currDate) {
            ArrayNode arrayMilestones = MAPPER.createArrayNode();
            for (Milestone milestone : milestoneList) {
                arrayMilestones.add(milestone.createOutput(MAPPER, currDate));
            }
            objNode.set("milestones", arrayMilestones);
            return this;
        }

        public OutputBuilder assignDeveloperTicketsList(final List<Ticket> ticketList) {
            ArrayNode arrayTickets = MAPPER.createArrayNode();
            for (Ticket ticket : ticketList) {
                ObjectNode ticketNode = ticket.createOutput(MAPPER);
                ticketNode.put("assignedAt", ticket.getAssignedAt());
                ticketNode.put("reportedBy", ticket.getReportedBy());
                ticketNode.set("comments", ticket.createCommentNode(MAPPER));
                arrayTickets.add(ticketNode);
            }
            objNode.set("assignedTickets", arrayTickets);
            return this;
        }

        public OutputBuilder assignTicketHistory(
                final Map<Ticket, ArrayList<TicketEvent>> history) {
            ArrayNode ticketHistory = MAPPER.createArrayNode();
            for (Map.Entry<Ticket, ArrayList<TicketEvent>> historyEntry : history.entrySet()) {
                ObjectNode ticketNode = MAPPER.createObjectNode();
                ticketNode.put("id", historyEntry.getKey().getId());
                ticketNode.put("title", historyEntry.getKey().getTitle());
                ticketNode.put("status", historyEntry.getKey().getStatus());
                ArrayNode actions = MAPPER.createArrayNode();
                for (TicketEvent event : historyEntry.getValue()) {
                    ObjectNode eventNode = MAPPER.createObjectNode();
                    if (event.getMilestone() != null) {
                        eventNode.put("milestone", event.getMilestone());
                    }
                    if (event.getOldStatus() != null && event.getNewStatus() != null) {
                        eventNode.put("from", event.getOldStatus());
                        eventNode.put("to", event.getNewStatus());
                    }
                    eventNode.put("by", event.getUsername());
                    eventNode.put("timestamp", event.getTimestamp().toString());
                    eventNode.put("action", event.getAction());
                    actions.add(eventNode);
                }
                ticketNode.set("actions", actions);
                ticketNode.set("comments", historyEntry.getKey().createCommentNode(MAPPER));
                ticketHistory.add(ticketNode);
            }
            objNode.set("ticketHistory", ticketHistory);
            return this;
        }

        public OutputBuilder assignSearchTicket(final List<Ticket> ticketList,
                                                final List<String> keyWords) {
            objNode.put("searchType", "TICKET");
            ArrayNode ticketArray = MAPPER.createArrayNode();
            for (Ticket ticket : ticketList) {
                ObjectNode ticketNode = ticket.createOutput(MAPPER);
                ticketNode.put("solvedAt", ticket.getSolvedAt());
                ticketNode.put("reportedBy", ticket.getReportedBy());
                if (keyWords != null) {
                    List<String> sortedKeyWords = keyWords.stream().sorted().toList();
                    ArrayNode matchingKeyWordsArray = MAPPER.createArrayNode();
                    for (String keyWord : sortedKeyWords) {
                        if (ticket.getTitle().contains(keyWord)
                                || ticket.getDescription().contains(keyWord)) {
                            matchingKeyWordsArray.add(keyWord);
                        }
                    }
                    ticketNode.set("matchingWords", matchingKeyWordsArray);
                }
                ticketArray.add(ticketNode);
            }
            objNode.set("results", ticketArray);
            return this;
        }

        public OutputBuilder assignSearchDeveloper(final List<Developer> developerList) {
            objNode.put("searchType", "DEVELOPER");
            ArrayNode developerArray = MAPPER.createArrayNode();
            List<Developer> devList = developerList.stream()
                    .sorted(Comparator.comparing(Developer::getUsername))
                    .toList();
            for (Developer dev : devList) {
                ObjectNode devNode  = dev.createOutput(MAPPER);
                developerArray.add(devNode);
            }
            objNode.set("results", developerArray);
            return this;
        }

        public OutputBuilder assignNotifications(final List<String> notificationList) {
            ArrayNode notificationArray = MAPPER.createArrayNode();
            for (String notification : notificationList) {
                notificationArray.add(notification);
            }
            objNode.set("notifications", notificationArray);
            return this;
        }

        public Output build() {
            return new Output(this);
        }
    }

    private Output(OutputBuilder builder) {
        this.objNode = builder.objNode;
    }

    public ObjectNode getObjNode() {
        return objNode;
    }
}
