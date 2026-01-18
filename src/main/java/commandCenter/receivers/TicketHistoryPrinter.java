package commandCenter.receivers;

import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import users.User;
import tickets.Ticket;
import history.TicketEvent;

public class TicketHistoryPrinter {
    public Map<Ticket, ArrayList<TicketEvent>> printHistory(final User user) {
        Map<Ticket, ArrayList<TicketEvent>> historyMap = new LinkedHashMap<>();
        Map<Ticket, ArrayList<TicketEvent>> userHistory = user.getHistoryMap();
        for (Ticket currTicket : user.getUserTickets()) {
            historyMap.put(currTicket, userHistory.get(currTicket));
        }
        return historyMap;
    }
}
