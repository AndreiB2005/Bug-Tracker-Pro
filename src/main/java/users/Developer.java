package users;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import fileio.UserInput;
import expertise.ExpertiseArea;
import tickets.Ticket;

public class Developer extends User {
    private final String hireDate;
    private final ExpertiseArea expertiseArea;
    private final Seniority seniority;
    private final List<ExpertiseArea> expertiseList;

    private enum Seniority {
        JUNIOR,
        MID,
        SENIOR
    }

    public Developer(final UserInput userInput) {
        super(userInput);
        hireDate = userInput.getHireDate();
        expertiseArea = ExpertiseArea.valueOf(userInput.getExpertiseArea());
        seniority = Seniority.valueOf(userInput.getSeniority());
        expertiseList = createExpertiseList();
    }

    private List<ExpertiseArea> createExpertiseList() {
        List<ExpertiseArea> list;
        if (expertiseArea != ExpertiseArea.FULLSTACK) {
            list = new ArrayList<>();
            list.add(expertiseArea);
            switch (expertiseArea) {
                case FRONTEND -> list.add(ExpertiseArea.DESIGN);
                case BACKEND -> list.add(ExpertiseArea.DB);
                case DESIGN -> list.add(ExpertiseArea.FRONTEND);
            }
        } else {
            list = Arrays.stream(ExpertiseArea.values())
                    .limit(ExpertiseArea.values().length - 1)
                    .toList();
        }
        return list;
    }

    public List<Ticket> getUserTickets(final List<Ticket> tickets) {
        return getUserMilestones().stream()
                .flatMap(milestone -> milestone.getTickets().stream())
                .filter(ticket -> "OPEN".equals(ticket.getStatus()))
                .sorted(
                        Comparator.comparing(Ticket::getCreatedAt)
                                .thenComparing(Ticket::getId)
                )
                .toList();
    }
}
