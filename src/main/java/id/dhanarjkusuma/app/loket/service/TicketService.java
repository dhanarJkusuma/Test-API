package id.dhanarjkusuma.app.loket.service;

import id.dhanarjkusuma.app.loket.domain.Ticket;
import id.dhanarjkusuma.app.loket.exception.TicketNotFoundException;

public interface TicketService {
    Ticket create(Ticket ticket);
    Ticket update(Long id, Ticket ticket) throws TicketNotFoundException;
    Ticket saveUpdatedTicket(Ticket ticket);
    Ticket getTicket(Long id) throws TicketNotFoundException;
    void destroy(Long id) throws TicketNotFoundException;
}
