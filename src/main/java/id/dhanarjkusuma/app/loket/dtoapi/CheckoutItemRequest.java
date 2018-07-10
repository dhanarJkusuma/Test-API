package id.dhanarjkusuma.app.loket.dtoapi;

import javax.validation.constraints.NotNull;

public class CheckoutItemRequest {

    @NotNull(message = "Item ticketId cannot be null.")
    private Long ticketId;

    @NotNull(message = "Item quantity cannot be null.")
    private Long quantity;

    public CheckoutItemRequest() {
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
