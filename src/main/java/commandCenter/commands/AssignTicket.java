package commandCenter.commands;

import fileio.CommandInput;
import users.Developer;
import tickets.Ticket;
import milestones.Milestone;
import commandCenter.receivers.TicketDispenser;
import commandCenter.Output;
import commandCenter.errors.CommandError;
import commandCenter.errors.WrongExpertiseAreaError;
import commandCenter.errors.WrongSeniorityError;
import commandCenter.errors.NotOpenError;
import commandCenter.errors.WrongMilestoneError;
import commandCenter.errors.BlockedMilestoneError;

public class AssignTicket extends Command {
    private final TicketDispenser ticketDispenser;
    private final Developer developer;
    private final int ticketId;

    public AssignTicket(final CommandInput input, final TicketDispenser ticketDispenser,
                        final Developer developer) {
        super(input);
        this.ticketDispenser = ticketDispenser;
        this.developer = developer;
        ticketId = input.getTicketID();
    }

    public Output execute() throws CommandError {
        Ticket currTicket = ticketDispenser.getTicket(ticketId);
        String ticketExpertise = currTicket.getExpertiseArea();
        if (!developer.getExpertiseList().contains(ticketExpertise)) {
            throw new WrongExpertiseAreaError(Developer.getRequiredExpertise(ticketExpertise),
                    developer, currTicket.getId());
        }
        if (!developer.getPriorities().contains(currTicket.getBusinessPriority()) ||
                !developer.getTicketTypes().contains(currTicket.getType())) {
            String ticketPriority = currTicket.getBusinessPriority();
            String ticketType = currTicket.getType();
            throw new WrongSeniorityError(
                    Developer.getRequiredSeniority(ticketPriority, ticketType), developer,
                    currTicket.getId());
        }
        if (!currTicket.getStatus().equals("OPEN")) {
            throw new NotOpenError();
        }
        Milestone currMilestone = ticketDispenser.getMilestone(currTicket);
        if (!currMilestone.getAssignedDevs().contains(developer)) {
            throw new WrongMilestoneError(developer.getUsername(), currMilestone.getName());
        }
        if (!currMilestone.getBlockedBy().isEmpty()) {
            throw new BlockedMilestoneError(currTicket.getId(), currMilestone.getName());
        }
        ticketDispenser.assignTicket(developer, currTicket);
        return null;
    }
}
