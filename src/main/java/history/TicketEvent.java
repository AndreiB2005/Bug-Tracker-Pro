package history;

import lombok.Getter;
import java.time.LocalDate;

@Getter
public class TicketEvent {
    private final String action;
    private final String username;
    private final LocalDate timestamp;
    private final String oldStatus;
    private final String newStatus;
    private final String milestone;

    public static class TicketEventBuilder {
        private final String action;
        private final String username;
        private final LocalDate timestamp;
        private String oldStatus;
        private String newStatus;
        private String milestone;

        public TicketEventBuilder(final String action, final String username,
                                  final LocalDate timestamp) {
            this.action = action;
            this.username = username;
            this.timestamp = timestamp;
        }

        public TicketEventBuilder addMilestone(final String milestone) {
            this.milestone = milestone;
            return this;
        }

        public TicketEventBuilder changeStatus(final String oldStatus, final String newStatus) {
            this.oldStatus = oldStatus;
            this.newStatus = newStatus;
            return this;
        }

        public TicketEvent build() {
            return new TicketEvent(this);
        }
    }

    private TicketEvent(final TicketEventBuilder builder) {
        this.action = builder.action;
        this.username = builder.username;
        this.timestamp = builder.timestamp;
        this.oldStatus = builder.oldStatus;
        this.newStatus = builder.newStatus;
        this.milestone = builder.milestone;
    }
}
