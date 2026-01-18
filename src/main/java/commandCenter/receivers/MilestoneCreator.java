package commandCenter.receivers;

import java.util.List;
import java.util.ArrayList;
import main.AppBrain;
import milestones.Milestone;
import fileio.CommandInput;
import tickets.Ticket;
import users.Developer;
import history.TicketEvent;

public class MilestoneCreator {
    private final AppBrain brain;

    public MilestoneCreator(final AppBrain brain) {
        this.brain = brain;
    }

    public int checkMilestone(final CommandInput commandInput) {
        for (Milestone milestone : brain.getMilestones()) {
            List<Integer> ticketsId = new ArrayList<>();
            for (Ticket ticket : milestone.getTickets()) {
                ticketsId.add(ticket.getId());
            }
            for (Integer id : commandInput.getTickets()) {
                if (ticketsId.contains(id)) {
                    return id;
                }
            }
        }
        return -1;
    }

    public String getMilestone(final int id) {
        for (Milestone milestone : brain.getMilestones()) {
            for (Ticket ticket : milestone.getTickets()) {
                if (ticket.getId() == id) {
                    return milestone.getName();
                }
            }
        }
        return null;
    }

    public void addMilestone(final CommandInput commandInput) {
        Milestone newMilestone = new Milestone(commandInput, brain);
        brain.getMilestones().add(newMilestone);
        newMilestone.getManagerMilestone().getUserMilestones().add(newMilestone);
        List<Developer> devsList = newMilestone.getAssignedDevs();
        for (Developer currDev : devsList) {
            currDev.getUserMilestones().add(newMilestone);
        }
        List<Milestone> blockingFor = commandInput.getBlockingFor().stream()
                        .map(blockedName -> brain.getMilestones().stream()
                                .filter(milestone -> blockedName.equals(milestone.getName()))
                                .findFirst()
                                .orElse(null))
                        .toList();
        newMilestone.getBlockingFor().addAll(blockingFor);
        for (Milestone blockedMilestone : blockingFor) {
            blockedMilestone.getBlockedBy().add(newMilestone);
        }
        TicketEvent event = new TicketEvent.TicketEventBuilder(
                "ADDED_TO_MILESTONE",
                newMilestone.getManagerMilestone().getUsername(),
                newMilestone.getCreatedAt())
                .addMilestone(newMilestone.getName())
                .build();
        for (Ticket ticket : newMilestone.getTickets()) {
            ticket.getTicketHistory().add(event);
        }
    }
}
