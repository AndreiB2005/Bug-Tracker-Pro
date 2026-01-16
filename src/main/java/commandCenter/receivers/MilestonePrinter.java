package commandCenter.receivers;

import java.util.List;
import java.util.Comparator;
import milestones.Milestone;
import users.User;

public class MilestonePrinter {
    private final List<Milestone> milestoneList;

    public MilestonePrinter(final List<Milestone> milestoneList) {
        this.milestoneList = milestoneList;
    }

    public List<Milestone> printMilestoneList(final User currUser) {
        List<Milestone> milestoneList = currUser.getUserMilestones();
        return milestoneList.stream()
                .sorted(
                        Comparator.comparing(Milestone::getDueDate)
                                .thenComparing(Milestone::getName)
                )
                .toList();
    }
}
