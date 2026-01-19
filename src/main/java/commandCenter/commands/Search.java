package commandCenter.commands;

import java.util.List;
import fileio.CommandInput;
import fileio.SearchInput;
import users.SearchMember;
import users.Developer;
import tickets.Ticket;
import visitors.*;
import commandCenter.receivers.SearchEngine;
import commandCenter.Output;

public class Search extends Command {
    private final SearchEngine engine;
    private final SearchInput searchInput;

    public Search(final CommandInput input, final SearchEngine engine) {
        super(input);
        this.engine = engine;
        searchInput = input.getFilters();
    }

    public Output execute() {
        SearchMember member = engine.getMember(getUsername());
        if (searchInput.getSearchType().equals("TICKET")) {
            Visitor<Ticket> visitor = new TicketSearch();
            List<Ticket> ticketList = engine.getTicketResults(member, searchInput, visitor);
            return new Output.OutputBuilder(this)
                    .assignSearchTicket(ticketList, searchInput.getKeywords())
                    .build();
        } else if (searchInput.getSearchType().equals("DEVELOPER")) {
            Visitor<Developer> visitor = new DeveloperSearch();
            List<Developer> developerList = engine.getDeveloperResults(member, searchInput,
                    visitor);
            return new Output.OutputBuilder(this)
                    .assignSearchDeveloper(developerList)
                    .build();
        }
        return null;
    }
}
