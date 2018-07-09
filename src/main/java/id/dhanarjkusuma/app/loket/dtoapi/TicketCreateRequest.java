package id.dhanarjkusuma.app.loket.dtoapi;

import id.dhanarjkusuma.app.loket.domain.TicketFlag;
import id.dhanarjkusuma.app.loket.helper.ValidateEnum;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class TicketCreateRequest {

    @NotNull(message = "Ticket name cannot be null.")
    private String name;

    @NotNull(message = "Ticket eventId cannot be null.")
    private Long eventId;

    @NotNull(message = "Ticket quantity cannot be null.")
    private Long quantity;

    @NotNull(message = "Ticket actualQuantity cannot be null.")
    private Long actualQuantity;

    @NotNull(message = "Ticket description cannot be null.")
    private String description;

    @NotNull(message = "Ticket flag cannot be null.")
    @ValidateEnum(enumClass = TicketFlag.class)
    private TicketFlag flag;

    @NotNull(message = "Ticket startDate cannot be null.")
    private Date startDate;

    @NotNull(message = "Ticket endDate cannot be null.")
    private Date endDate;

    public TicketCreateRequest() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
