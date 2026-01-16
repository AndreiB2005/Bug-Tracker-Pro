package commandCenter.commands;

import java.time.LocalDate;
import java.util.List;
import fileio.CommandInput;
import milestones.Milestone;
import commandCenter.receivers.MilestonePrinter;
import commandCenter.Output;

public class ViewMilestones extends Command {
    private final MilestonePrinter milestonePrinter;
    private final LocalDate currDate;

    public ViewMilestones(final CommandInput input,
            final MilestonePrinter milestonePrinter, final LocalDate currDate) {
        super(input);
        this.milestonePrinter = milestonePrinter;
        this.currDate = currDate;
    }

    public Output execute() {
        List<Milestone> milestoneList = milestonePrinter.printMilestoneList(getCurrUser());
        return new Output.OutputBuilder(this)
                .assignMilestoneList(milestoneList, currDate)
                .build();
    }
}
