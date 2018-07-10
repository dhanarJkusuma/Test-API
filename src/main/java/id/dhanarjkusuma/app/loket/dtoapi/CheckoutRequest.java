package id.dhanarjkusuma.app.loket.dtoapi;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CheckoutRequest {
    @NotNull(message = "Checkout customer cannot be null.")
    @Valid
    private CheckoutCustomerRequest customer;

    @NotNull(message = "Checkout eventId cannot be null.")
    private Long eventId;

    @NotNull(message = "Checkout items cannot be null.")
    @Valid
    private List<CheckoutItemRequest> items;

    public CheckoutRequest() {
    }

    public CheckoutCustomerRequest getCustomer() {
        return customer;
    }

    public void setCustomer(CheckoutCustomerRequest customer) {
        this.customer = customer;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public List<CheckoutItemRequest> getItems() {
        return items;
    }

    public void setItems(List<CheckoutItemRequest> items) {
        this.items = items;
    }
}
