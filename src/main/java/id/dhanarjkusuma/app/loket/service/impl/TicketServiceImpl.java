package id.dhanarjkusuma.app.loket.service.impl;

import id.dhanarjkusuma.app.loket.domain.Ticket;
import id.dhanarjkusuma.app.loket.exception.TicketNotFoundException;
import id.dhanarjkusuma.app.loket.repository.TicketRepository;
import id.dhanarjkusuma.app.loket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TicketServiceImpl implements TicketService {

    private TicketRepository ticketRepository;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket create(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket update(Long id, Ticket ticket) throws TicketNotFoundException {
        Ticket existTicket = ticketRepository.findById(id).orElseThrow(() -> new TicketNotFoundException("Unknown ticket with id : " + id));
        existTicket.setName(ticket.getName());
        existTicket.setTicketCount(ticket.getTicketCount());
        existTicket.setActualTicketCount(ticket.getTicketCount());
        existTicket.setDescription(ticket.getDescription());
        existTicket.setFlag(ticket.getFlag());
        existTicket.setStartDate(ticket.getStartDate());
        existTicket.setEndDate(ticket.getEndDate());
        existTicket.setCreatedAt(ticket.getCreatedAt());
        existTicket.setUpdatedAt(new Date());
        return ticketRepository.save(existTicket);
    }

    @Override
    public Ticket getTicket(Long id) throws TicketNotFoundException {
        return ticketRepository.findById(id).orElseThrow(() -> new TicketNotFoundException("Unknown ticket with id : " + id));
    }

    @Override
    public void destroy(Long id) throws TicketNotFoundException {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new TicketNotFoundException("Unknown ticket with id : " + id));
        ticket.setIsDeleted(true);
        ticketRepository.save(ticket);
    }
}
