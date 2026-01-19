package commandCenter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import main.AppBrain;
import fileio.CommandInput;
import factories.ticketFactories.*;
import users.User;
import users.Developer;
import commandCenter.commands.*;
import commandCenter.errors.CommandError;
import commandCenter.errors.UserNotFoundError;
import commandCenter.errors.NotAllowedError;

public class CommandHandler {
    private final RemoteControl remote = new RemoteControl();
    private final AppBrain brain;
    private int currTicketId = 0;

    private static final Map<String, List<String>> accessMap = Map.ofEntries(
            Map.entry("reportTicket", List.of("REPORTER")),
            Map.entry("viewTickets", List.of("DEVELOPER", "MANAGER", "REPORTER")),
            Map.entry("lostInvestors", List.of("MANAGER")),
            Map.entry("createMilestone", List.of("MANAGER")),
            Map.entry("viewMilestones", List.of("MANAGER", "DEVELOPER")),
            Map.entry("assignTicket", List.of("DEVELOPER")),
            Map.entry("viewAssignedTickets", List.of("DEVELOPER")),
            Map.entry("undoAssignTicket", List.of("DEVELOPER")),
            Map.entry("addComment", List.of("REPORTER", "DEVELOPER")),
            Map.entry("undoAddComment", List.of("REPORTER", "DEVELOPER")),
            Map.entry("changeStatus", List.of("DEVELOPER")),
            Map.entry("undoChangeStatus", List.of("DEVELOPER")),
            Map.entry("viewTicketHistory", List.of("DEVELOPER", "MANAGER")),
            Map.entry("search", List.of("DEVELOPER", "MANAGER"))
    );

    public CommandHandler(final AppBrain brain) {
        this.brain = brain;
    }
    
    public Output handleCommand(final CommandInput input) {
        Command command = generateCommand(input);
        assert command != null;
        Output commandOutput;
        try {
            User currUser = verifyUser(input.getUsername());
            if (currUser == null) {
                throw new UserNotFoundError(input.getUsername());
            }
            if (!hasAccess(input.getCommand(), currUser)) {
                List<String> requiredRole = accessMap.get(input.getCommand());
                throw new NotAllowedError(requiredRole, currUser.getRole());
            }
            command.setCurrUser(currUser);
            remote.setCommand(command);
            commandOutput = remote.executeCommand();
        } catch (CommandError error) {
            commandOutput = new Output.OutputBuilder(command)
                    .assignError(error.getMessage())
                    .build();
        }
        return commandOutput;
    }
    
    private User verifyUser(final String username) {
        for (User currUser : brain.getUsers()) {
            if (username.equals(currUser.getUsername())) {
                return currUser;
            }
        }
        return null;
    }
    
    private boolean hasAccess(final String commandName, final User currUser) {
        List<String> commandAccess = accessMap.get(commandName);
        for (String userRole : commandAccess) {
            if (userRole.equals(currUser.getRole())) {
                return true;
            }
        }
        return false;
    }

    private Command generateCommand(final CommandInput input) {
        switch (input.getCommand()) {
            case "reportTicket":
                TicketFactory factory = brain.getTicketFactoryMap()
                        .get(input.getParams().getType());
                LocalDate timestamp = LocalDate.parse(input.getTimestamp());
                factory.setCurrId(currTicketId);
                currTicketId++;
                return new ReportTicket(input, factory, brain.getTickets(),
                        brain.isTestPhase(timestamp));
            case "viewTickets":
                return new ViewTickets(input, brain.getTicketPrinter());
            case "lostInvestors":
                return new LostInvestors(input, brain);
            case "createMilestone":
                return new CreateMilestone(input, brain.getMilestoneCreator());
            case "viewMilestones":
                return new ViewMilestones(input, brain.getMilestonePrinter(), brain.getCurrDate());
            case "assignTicket":
                return new AssignTicket(input, brain.getTicketDispenser(),
                        getDeveloper(input.getUsername()));
            case "viewAssignedTickets":
                return new ViewAssignedTickets(input, brain.getTicketPrinterDev(),
                        getDeveloper(input.getUsername()));
            case "undoAssignTicket":
                return new UndoAssignTicket(input, brain.getTicketRemover(),
                        getDeveloper(input.getUsername()));
            case "addComment":
                return new AddComment(input, brain.getCommentGenerator());
            case "undoAddComment":
                return new UndoAddComment(input, brain.getCommentRemover());
            case "changeStatus":
                return new ChangeStatus(input, brain.getTicketService());
            case "undoChangeStatus":
                return new UndoChangeStatus(input, brain.getTicketService());
            case "viewTicketHistory":
                return new ViewTicketHistory(input, brain.getHistoryPrinter());
            case "search":
                return new Search(input, brain.getSearchEngine());
            default:
                return null;
        }
    }

    private Developer getDeveloper(final String devName) {
        for (Developer currDev : brain.getDevelopers()) {
            if (devName.equals(currDev.getUsername())) {
                return currDev;
            }
        }
        return null;
    }
}
