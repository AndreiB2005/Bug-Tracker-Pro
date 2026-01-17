package main;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import fileio.InputLoader;
import fileio.UserInput;
import fileio.CommandInput;
import factories.userFactories.*;
import factories.ticketFactories.*;
import users.User;
import users.Developer;
import users.Manager;
import tickets.Ticket;
import milestones.Milestone;
import commandCenter.commands.*;
import commandCenter.CommandHandler;
import commandCenter.receivers.*;
import commandCenter.Output;

@Getter
public class AppBrain {
    private static final int TESTING_PERIOD = 12;

    private final List<User> users = new ArrayList<>();
    private final List<Developer> developers =  new ArrayList<>();
    private final List<Manager> managers = new ArrayList<>();
    private final List<CommandInput> commandInputs;
    private final List<Ticket> tickets = new ArrayList<>();
    private final List<Milestone> milestones = new ArrayList<>();
    private final List<ObjectNode> outputs;
    private final CommandHandler commandHandler;
    private final TicketPrinter ticketPrinter = new TicketPrinter(tickets);
    private final MilestoneCreator milestoneCreator = new MilestoneCreator(this);
    private final MilestonePrinter milestonePrinter = new MilestonePrinter(milestones);
    private final TicketDispenser ticketDispenser = new TicketDispenser(this);
    private final AssignedTicketPrinter ticketPrinterDev = new AssignedTicketPrinter();
    private final TicketRemover ticketRemover = new TicketRemover();
    private LocalDate testPhaseStart;
    private LocalDate currDate;
    private boolean stopRun = false;

    private static final Map<String, UserFactory> userFactoryMap = Map.of(
            "REPORTER", new ReporterFactory(),
            "DEVELOPER", new DeveloperFactory(),
            "MANAGER", new ManagerFactory()
    );

    private static final Map<String, TicketFactory> ticketFactoryMap = Map.of(
            "BUG", new BugFactory(),
            "FEATURE_REQUEST", new FeatureRequestFactory(),
            "UI_FEEDBACK", new UiFeedbackFactory()
    );

    public AppBrain(final InputLoader inputLoader, final List<ObjectNode> outputs) {
        createUserList(inputLoader.getUsers());
        commandInputs = inputLoader.getCommands();
        this.outputs = outputs;
        commandHandler = new CommandHandler(this);
        testPhaseStart = LocalDate.parse(commandInputs.getFirst().getTimestamp());
    }

    public void runApp() {
        for (CommandInput currInput : commandInputs) {
            currDate = LocalDate.parse(currInput.getTimestamp());
            for (Milestone currMilestone : milestones) {
                currMilestone.updateTickets(currDate);
            }
            Output commandOutput = commandHandler.handleCommand(currInput);
            if (stopRun) {
                break;
            }
            if (commandOutput != null) {
                outputs.add(commandOutput.getObjNode());
            }
        }
    }

    public void stopApp() {
        stopRun = true;
    }

    public void setTestingPeriod(final String timestamp) {
        testPhaseStart = LocalDate.parse(timestamp);
    }

    private void createUserList(List<UserInput> userInputList) {
        for (UserInput input : userInputList) {
            UserFactory factory = userFactoryMap.get(input.getRole());
            factory.addUser(this, input);
        }
    }

    public boolean isTestPhase(final LocalDate currTimestamp) {
        int testDays = (int) ChronoUnit.DAYS.between(testPhaseStart, currTimestamp) + 1;
        return testDays <= TESTING_PERIOD;
    }

    public Map<String, TicketFactory> getTicketFactoryMap() {
        return ticketFactoryMap;
    }
}
