package id.dhanarjkusuma.app.loket.dtoapi;

import java.math.BigDecimal;

public class OrderTransactionItemResponse {
    private String ticketName;
    private String ticketFlag;
    private String quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;

    public OrderTransactionItemResponse() {
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getTicketFlag() {
        return ticketFlag;
    }

    public void setTicketFlag(String ticketFlag) {
        this.ticketFlag = ticketFlag;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
