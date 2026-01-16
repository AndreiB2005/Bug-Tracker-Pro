package tickets;

import fileio.TicketInput;

public class FeatureRequest extends Ticket {
    private final BusinessValue businessValue;
    private final Demand customerDemand;

    private enum Demand {
        LOW,
        MEDIUM,
        HIGH,
        VERY_HIGH
    }

    public FeatureRequest(final TicketInput ticketInput, final int id, final String timestamp) {
        super(ticketInput, id, timestamp);
        businessValue = BusinessValue.valueOf(ticketInput.getBusinessValue());
        customerDemand = Demand.valueOf(ticketInput.getCustomerDemand());
    }
}
