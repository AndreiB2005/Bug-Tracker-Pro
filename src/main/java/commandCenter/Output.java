package commandCenter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.time.LocalDate;
import java.util.List;
import commandCenter.commands.Command;
import tickets.Ticket;
import milestones.Milestone;

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
                arrayTickets.add(ticket.createOutput(MAPPER));
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
