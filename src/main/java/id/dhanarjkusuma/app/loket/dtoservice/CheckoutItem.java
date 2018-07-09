package id.dhanarjkusuma.app.loket.dtoservice;

public class CheckoutItem {
    private Long ticketId;
    private Long quantity;

    public CheckoutItem() {
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
