package users;

import java.util.List;
import java.util.ArrayList;
import fileio.UserInput;
import tickets.Ticket;
import milestones.Milestone;
import strategies.TicketViewStrategy;
import commandCenter.errors.CommandError;

public abstract class User {
    private final String username;
    private final String email;
    private final Role role;
    private final List<Milestone> userMilestones = new ArrayList<>();
    private TicketViewStrategy strategy;

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

    public TicketViewStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(final TicketViewStrategy strategy) {
        this.strategy = strategy;
    }

    public abstract void checkComment(final Ticket ticket) throws CommandError;
}
