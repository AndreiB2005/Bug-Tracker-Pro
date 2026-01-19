package tickets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Getter;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import fileio.TicketInput;
import expertise.ExpertiseArea;
import history.TicketEvent;

public abstract class Ticket {
    private final int id;
    private final String type;
    private final String title;
    private Priority businessPriority;
    private Status status = Status.OPEN;
    private final ExpertiseArea expertiseArea;
    private final String description;
    private final String reportedBy;
    private final LocalDate createdAt;
    private LocalDate assignedAt = null;
    private LocalDate solvedAt = null;
    private String assignedTo = null;
    private final List<Comment> comments = new ArrayList<>();
    private final List<TicketEvent> ticketHistory = new ArrayList<>();

    private enum Priority {
        LOW(1),
        MEDIUM(2),
        HIGH(3),
        CRITICAL(4);

        private final int priorityLevel;

        Priority(final int priorityLevel) {
            this.priorityLevel = priorityLevel;
        }

        public int getPriorityLevel() {
            return priorityLevel;
        }

        public Priority getNextLevel() {
            return switch (this) {
                case LOW -> MEDIUM;
                case MEDIUM -> HIGH;
                case HIGH, CRITICAL -> CRITICAL;
            };
        }
    }

    private enum Status {
        OPEN,
        IN_PROGRESS,
        RESOLVED,
        CLOSED;

        public Status getNextStatus() {
            return switch (this) {
                case OPEN -> IN_PROGRESS;
                case IN_PROGRESS -> RESOLVED;
                case RESOLVED -> CLOSED;
                default -> this;
            };
        }

        public Status getPrevStatus() {
            return switch (this) {
                case CLOSED -> RESOLVED;
                case RESOLVED -> IN_PROGRESS;
                default -> this;
            };
        }
    }

    @Getter
    public static class Comment {
        private final String author;
        private final String content;
        private final LocalDate createdAt;

        public Comment(final String author, final String content, final LocalDate createdAt) {
            this.author = author;
            this.content = content;
            this.createdAt = createdAt;
        }
    }

    public Ticket(final TicketInput ticketInput, final int id, final String createdAt) {
        this.id = id;
        type = ticketInput.getType();
        title = ticketInput.getTitle();
        businessPriority = Priority.valueOf(ticketInput.getBusinessPriority());
        expertiseArea = ExpertiseArea.valueOf(ticketInput.getExpertiseArea());
        description = ticketInput.getDescription();
        reportedBy = ticketInput.getReportedBy();
        this.createdAt = LocalDate.parse(createdAt);
    }

    public ObjectNode createOutput(final ObjectMapper mapper) {
        ObjectNode ticketNode = mapper.createObjectNode();
        ticketNode.put("id", id);
        ticketNode.put("type", type);
        ticketNode.put("title", title);
        ticketNode.put("businessPriority", getBusinessPriority());
        ticketNode.put("status", getStatus());
        ticketNode.put("createdAt", getCreatedAt());
        return ticketNode;
    }

    public ArrayNode createCommentNode(final ObjectMapper mapper) {
        ArrayNode arrayComments = mapper.createArrayNode();
        for (Comment comment : comments) {
            ObjectNode commentNode = mapper.createObjectNode();
            commentNode.put("author", comment.getAuthor());
            commentNode.put("content", comment.getContent());
            commentNode.put("createdAt", comment.getCreatedAt().toString());
            arrayComments.add(commentNode);
        }
        return arrayComments;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getBusinessPriority() {
        return businessPriority.name();
    }

    public String getStatus() {
        return status.name();
    }

    public String getCreatedAt() {
        return createdAt.toString();
    }

    public String getAssignedAt() {
        return assignedAt != null ? assignedAt.toString() : "";
    }

    public String getSolvedAt() {
        return solvedAt != null ? solvedAt.toString() : "";
    }

    public String getAssignedTo() {
        return assignedTo != null ? assignedTo : "";
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public String getExpertiseArea() {
        return expertiseArea.toString();
    }

    public int getPriorityLevel() {
        return businessPriority.getPriorityLevel();
    }

    public List<TicketEvent> getTicketHistory() {
        return ticketHistory;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate wasCreatedAt() {
        return createdAt;
    }

    public void setBusinessPriority(final String priorityLevel) {
        businessPriority = Priority.valueOf(priorityLevel);
    }

    public void setNextPriorityLevel() {
        businessPriority = businessPriority.getNextLevel();
    }

    public void setStatus(final String statusLevel) {
        status = Status.valueOf(statusLevel);
    }

    public void upgradeStatus() {
        status = status.getNextStatus();
    }

    public void downgradeStatus() {
        status = status.getPrevStatus();
    }

    public void setAssignedAt(final LocalDate assignedAt) {
        this.assignedAt = assignedAt;
    }

    public void setAssignedTo(final String assignedTo) {
        this.assignedTo = assignedTo;
    }
}
