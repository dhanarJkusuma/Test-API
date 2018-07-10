package id.dhanarjkusuma.app.loket.dtoapi;

import id.dhanarjkusuma.app.loket.domain.TicketFlag;

public class TicketSummaryResponse {
    private Long ticketId;
    private String name;
    private Long quantity;
    private Long actualQuantity;
    private String description;
    private TicketFlag flag;
    private String startDate;
    private String endDate;

    public TicketSummaryResponse() {
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getActualQuantity() {
        return actualQuantity;
    }

    public void setActualQuantity(Long actualQuantity) {
        this.actualQuantity = actualQuantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketFlag getFlag() {
        return flag;
    }

    public void setFlag(TicketFlag flag) {
        this.flag = flag;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
