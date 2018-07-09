package id.dhanarjkusuma.app.loket.dtoservice;

import java.util.List;

public class Checkout {
    private CheckoutCustomer customer;
    private Long eventId;
    private List<CheckoutItem> items;

    public Checkout() {
    }

    public CheckoutCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(CheckoutCustomer customer) {
        this.customer = customer;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public List<CheckoutItem> getItems() {
        return items;
    }

    public void setItems(List<CheckoutItem> items) {
        this.items = items;
    }
}
