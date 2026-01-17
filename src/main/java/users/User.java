package users;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import fileio.UserInput;
import tickets.Ticket;
import milestones.Milestone;

public abstract class User {
    private final String username;
    private final String email;
    private final Role role;
    private final List<Milestone> userMilestones = new ArrayList<>();

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

    public abstract List<Ticket> getUserTickets(final List<Ticket> tickets);
}
