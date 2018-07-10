package id.dhanarjkusuma.app.loket.service.impl;

import id.dhanarjkusuma.app.loket.domain.Event;
import id.dhanarjkusuma.app.loket.domain.OrderTransaction;
import id.dhanarjkusuma.app.loket.domain.OrderTransactionItem;
import id.dhanarjkusuma.app.loket.domain.Ticket;
import id.dhanarjkusuma.app.loket.dtoservice.Checkout;
import id.dhanarjkusuma.app.loket.dtoservice.CheckoutCustomer;
import id.dhanarjkusuma.app.loket.dtoservice.CheckoutItem;
import id.dhanarjkusuma.app.loket.exception.EventNotFoundException;
import id.dhanarjkusuma.app.loket.exception.InvalidCheckoutException;
import id.dhanarjkusuma.app.loket.exception.OrderTransactionNotFoundException;
import id.dhanarjkusuma.app.loket.repository.OrderTransactionRepository;
import id.dhanarjkusuma.app.loket.service.EventService;
import id.dhanarjkusuma.app.loket.service.OrderTransactionService;
import id.dhanarjkusuma.app.loket.service.PublicIdGeneratorService;
import id.dhanarjkusuma.app.loket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderTranscationServiceImpl implements OrderTransactionService {

    private OrderTransactionRepository transactionRepository;
    private TicketService ticketService;
    private EventService eventService;
    private PublicIdGeneratorService generatorService;

    @Autowired
    public OrderTranscationServiceImpl(OrderTransactionRepository transactionRepository, TicketService ticketService, EventService eventService, PublicIdGeneratorService generatorService) {
        this.transactionRepository = transactionRepository;
        this.ticketService = ticketService;
        this.eventService = eventService;
        this.generatorService = generatorService;
    }

    public synchronized OrderTransaction create(Checkout checkout) {
        Event event;
        try{
             event = eventService.getEvent(checkout.getEventId());
        }catch (EventNotFoundException e){
            throw new InvalidCheckoutException("checkout.eventId", "Event Not Found. ");
        }

        Map<Ticket, Long> tickets = checkTicketItem(event.getId(), checkout.getItems());
        List<OrderTransactionItem> items = new ArrayList<>();
        OrderTransaction orderTransaction = new OrderTransaction();
        orderTransaction.setOrderDate(new Date());
        tickets.forEach((key, value) -> {
            OrderTransactionItem item = new OrderTransactionItem();
            item.setTicket(key);
            item.setPrice(key.getPrice());
            item.setQuantity(value);
            item.setTotalPrice(key.getPrice().multiply(BigDecimal.valueOf(value)));
            item.setTransaction(orderTransaction);
            items.add(item);
            updateStockTicket(key, value);
        });
        orderTransaction.setItems(items);
        orderTransaction.setEvent(event);
        CheckoutCustomer checkoutCustomer = checkout.getCustomer();
        orderTransaction.setCustomerFirstName(checkoutCustomer.getFirstName());
        orderTransaction.setCustomerLastName(checkoutCustomer.getLastName());
        orderTransaction.setCustomerEmail(checkoutCustomer.getEmail());
        orderTransaction.setCustomerDateOfBirth(checkoutCustomer.getDateOfBirth());
        orderTransaction.setCustomerGender(checkoutCustomer.getGender());
        orderTransaction.setCustomerPhone(checkoutCustomer.getPhone());
        orderTransaction.setPublicId(generatorService.generateTransactionPublicId());
        return transactionRepository.save(orderTransaction);
    }

    @Override
    public OrderTransaction getTransaction(Long id) throws OrderTransactionNotFoundException {
        return transactionRepository.findById(id).orElseThrow(() -> new OrderTransactionNotFoundException("Unknown transaction with id : "+ id));
    }

    @Override
    public OrderTransaction getTransactionByPublicId(String publicId) throws OrderTransactionNotFoundException {
        return transactionRepository.findFirstByPublicId(publicId).orElseThrow(() -> new OrderTransactionNotFoundException("Unknown transaction with public id : " + publicId));
    }

    @Override
    public Page<OrderTransaction> retrieveTransactions(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        return transactionRepository.findAll(pageable);
    }

    private Map<Ticket, Long> checkTicketItem(Long eventId, List<CheckoutItem> items){
        Map<Ticket, Long>  tickets = new HashMap<>();
        for(CheckoutItem checkoutItem : items){
            Ticket existTicket = ticketService.getTicket(checkoutItem.getTicketId());
            if(!existTicket.getEvent().getId().equals(eventId)){
                throw new InvalidCheckoutException("checkout.items.ticketId", "Tickets do not match. Tickets must have the same event");
            }
            if(existTicket.getActualTicketCount() - checkoutItem.getQuantity() < 0){
                throw new InvalidCheckoutException("checkout.items.quantity", "The number of tickets is insufficient.");
            }
            tickets.computeIfAbsent(existTicket, k -> checkoutItem.getQuantity());
            tickets.computeIfPresent(existTicket, ((ticket, aLong) -> aLong + checkoutItem.getQuantity()));
        }
        return tickets;
    }

    private void updateStockTicket(Ticket ticket, Long qty){
        long currentTicket = ticket.getActualTicketCount() - qty;
        ticket.setActualTicketCount(currentTicket);
        ticketService.saveUpdatedTicket(ticket);
    }

}
