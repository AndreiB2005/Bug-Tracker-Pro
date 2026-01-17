package users;

import java.util.List;
import java.util.Comparator;
import fileio.UserInput;
import tickets.Ticket;

public class Manager extends User {
    private final String hireDate;
    private final List<String> subordinates;

    public Manager(final UserInput userInput) {
        super(userInput);
        hireDate = userInput.getHireDate();
        subordinates = userInput.getSubordinates();
    }

    public void checkComment(final Ticket ticket) {
    }
}
