package id.dhanarjkusuma.app.loket.dtoapi;

import java.util.List;

public class OrderTransactionResponse {
    private String invoiceNo;
    private String orderDate;
    private EventResponse event;
    private OrderTransactionCustomerResponse customer;
    private List<OrderTransactionItemResponse> items;

    public OrderTransactionResponse() {
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public EventResponse getEvent() {
        return event;
    }

    public void setEvent(EventResponse event) {
        this.event = event;
    }

    public OrderTransactionCustomerResponse getCustomer() {
        return customer;
    }

    public void setCustomer(OrderTransactionCustomerResponse customer) {
        this.customer = customer;
    }

    public List<OrderTransactionItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderTransactionItemResponse> items) {
        this.items = items;
    }
}
