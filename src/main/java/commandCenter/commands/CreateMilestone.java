package commandCenter.commands;

import fileio.CommandInput;
import milestones.Milestone;
import commandCenter.receivers.MilestoneCreator;
import commandCenter.Output;
import commandCenter.errors.TicketInMilestoneError;

public class CreateMilestone extends Command {
    private final MilestoneCreator creator;
    private final CommandInput input;

    public CreateMilestone(final CommandInput input, final MilestoneCreator creator) {
        super(input);
        this.input = input;
        this.creator = creator;
    }

    public Output execute() throws TicketInMilestoneError {
        int checkMilestone = creator.checkMilestone(input);
        if (checkMilestone >= 0) {
            throw new TicketInMilestoneError(creator.getMilestone(checkMilestone), checkMilestone);
        }
        creator.addMilestone(input);
        return null;
    }
}
