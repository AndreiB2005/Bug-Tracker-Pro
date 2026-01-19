package users;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import fileio.UserInput;
import tickets.Ticket;
import milestones.Milestone;
import strategies.TicketViewStrategy;
import history.TicketEvent;
import commandCenter.errors.CommandError;

public abstract class User {
    private final String username;
    private final String email;
    private final Role role;
    private final List<Milestone> userMilestones = new ArrayList<>();
    private TicketViewStrategy viewStrategy;
    private final Map<Ticket, ArrayList<TicketEvent>> historyMap = new HashMap<>();

    private enum Role {
        REPORTER,
        DEVELOPER,
        MANAGER
    }

    public User(final UserInput userInput) {
        username = userInput.getUsername();
        email = userInput.getEmail();
        role = Role.valueOf(userInput.getRole());
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role.name();
    }

    public List<Milestone> getUserMilestones() {
        return userMilestones;
    }

    public TicketViewStrategy getViewStrategy() {
        return viewStrategy;
    }

    public Map<Ticket, ArrayList<TicketEvent>> getHistoryMap() {
        return historyMap;
    }

    public void setViewStrategy(final TicketViewStrategy viewStrategy) {
        this.viewStrategy = viewStrategy;
    }

    public abstract void checkComment(final Ticket ticket) throws CommandError;

    public abstract List<Ticket> getUserTickets();
}
