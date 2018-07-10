package id.dhanarjkusuma.app.loket.dtoapi;

import java.util.List;

public class EventDetailResponse {
    private EventResponse event;
    private List<TicketSummaryResponse> tickets;

    public EventResponse getEvent() {
        return event;
    }

    public void setEvent(EventResponse event) {
        this.event = event;
    }

    public List<TicketSummaryResponse> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketSummaryResponse> tickets) {
        this.tickets = tickets;
    }
}
