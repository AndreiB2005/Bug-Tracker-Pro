package users;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import fileio.UserInput;
import fileio.SearchInput;
import tickets.Ticket;
import visitors.Visitor;

public class Manager extends User implements SearchMember {
    private final String hireDate;
    private final List<Developer> subordinates = new ArrayList<>();

    public Manager(final UserInput userInput) {
        super(userInput);
        hireDate = userInput.getHireDate();
    }

    public void checkComment(final Ticket ticket) {
    }

    public List<Ticket> getUserTickets() {
        return getUserMilestones().stream()
                .flatMap(milestone -> milestone.getTickets().stream())
                .sorted(
                        Comparator.comparing(Ticket::getCreatedAt)
                                .thenComparing(Ticket::getId)
                )
                .toList();
    }

    public void addSubordinates(final List<Developer> allDevelopers,
                                final List<String> subordinatesNames) {
        for (String devName : subordinatesNames) {
            for (Developer currDeveloper : allDevelopers) {
                if (currDeveloper.getUsername().equals(devName)) {
                    subordinates.add(currDeveloper);
                    break;
                }
            }
        }
    }

    public <T> List<T> acceptList(final Visitor<T> visitor, final SearchInput input,
                                   final List<T> list) {
        return visitor.returnFilteredList(this, input, list);
    }

    public String getMemberName() {
        return getUsername();
    }
}
