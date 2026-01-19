package commandCenter.receivers;

import java.util.List;
import main.AppBrain;
import fileio.SearchInput;
import users.SearchMember;
import users.Developer;
import tickets.Ticket;
import visitors.Visitor;

public class SearchEngine {
    private final List<SearchMember> memberList;
    private final List<Ticket> ticketList;
    private final List<Developer> developerList;

    public SearchEngine(final AppBrain brain) {
        memberList = brain.getMembers();
        ticketList = brain.getTickets();
        developerList = brain.getDevelopers();
    }

    public SearchMember getMember(final String memberName) {
        for (SearchMember member : memberList) {
            if (member.getMemberName().equals(memberName)) {
                return member;
            }
        }
        return null;
    }

    public List<Ticket> getTicketResults(final SearchMember member, final SearchInput input,
                                         final Visitor<Ticket> ticketVisitor) {
        return member.acceptList(ticketVisitor, input, ticketList);
    }

    public List<Developer> getDeveloperResults(final SearchMember member,
                                               final SearchInput input,
                                               final Visitor<Developer> developerVisitor) {
        return member.acceptList(developerVisitor, input, developerList);
    }
}
