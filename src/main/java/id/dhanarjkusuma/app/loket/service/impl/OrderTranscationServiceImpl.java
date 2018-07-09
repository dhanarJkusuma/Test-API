package id.dhanarjkusuma.app.loket.service.impl;

import id.dhanarjkusuma.app.loket.domain.Event;
import id.dhanarjkusuma.app.loket.domain.OrderTransaction;
import id.dhanarjkusuma.app.loket.domain.OrderTransactionItem;
import id.dhanarjkusuma.app.loket.domain.Ticket;
import id.dhanarjkusuma.app.loket.dtoservice.Checkout;
import id.dhanarjkusuma.app.loket.dtoservice.CheckoutCustomer;
import id.dhanarjkusuma.app.loket.dtoservice.CheckoutItem;
import id.dhanarjkusuma.app.loket.exception.InvalidCheckoutException;
import id.dhanarjkusuma.app.loket.exception.OrderTransactionNotFoundException;
import id.dhanarjkusuma.app.loket.repository.OrderTransactionRepository;
import id.dhanarjkusuma.app.loket.service.EventService;
import id.dhanarjkusuma.app.loket.service.OrderTransactionService;
import id.dhanarjkusuma.app.loket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderTranscationServiceImpl implements OrderTransactionService {

    private OrderTransactionRepository transactionRepository;
    private TicketService ticketService;
    private EventService eventService;

    @Autowired
    public OrderTranscationServiceImpl(OrderTransactionRepository transactionRepository, TicketService ticketService, EventService eventService) {
        this.transactionRepository = transactionRepository;
        this.ticketService = ticketService;
        this.eventService = eventService;
    }

    @Override
    public synchronized OrderTransaction create(Checkout checkout) {
        Event event = eventService.getEvent(checkout.getEventId());
        Map<Ticket, Long> tickets = checkTicketItem(checkout.getItems());
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
        });
        orderTransaction.setEvent(event);
        CheckoutCustomer checkoutCustomer = checkout.getCustomer();
        orderTransaction.setCustomerFirstName(checkoutCustomer.getFirstName());
        orderTransaction.setCustomerLastName(checkoutCustomer.getLastName());
        orderTransaction.setCustomerEmail(checkoutCustomer.getEmail());
        orderTransaction.setCustomerDateOfBirth(checkoutCustomer.getDateOfBirth());
        orderTransaction.setCustomerGender(checkoutCustomer.getGender());
        orderTransaction.setCustomerPhone(checkoutCustomer.getPhone());
        return transactionRepository.save(orderTransaction);
    }

    @Override
    public OrderTransaction getTransaction(Long id) throws OrderTransactionNotFoundException {
        return null;
    }

    private Map<Ticket, Long> checkTicketItem(List<CheckoutItem> items){
        Map<Ticket, Long>  tickets = new HashMap<>();
        for(CheckoutItem checkoutItem : items){
            Ticket existTicket = ticketService.getTicket(checkoutItem.getTicketId());
            if(existTicket.getActualTicketCount() - checkoutItem.getQuantity() < 0){
                throw new InvalidCheckoutException("The number of tickets is insufficient");
            }
            tickets.computeIfAbsent(existTicket, k -> checkoutItem.getQuantity());
            tickets.computeIfPresent(existTicket, ((ticket, aLong) -> aLong + checkoutItem.getQuantity()));
        }
        return tickets;
    }

}
