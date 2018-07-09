package id.dhanarjkusuma.app.loket.dtoapi;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

public class EventUpdateRequest {

    @NotNull(message = "Event name cannot be null.")
    private String name;

    @NotNull(message = "Event organizerName cannot be null.")
    private String organizerName;

    @NotNull(message = "Event description cannot be null.")
    private String description;

    @NotNull(message = "Event startDate cannot be null.")
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    private Date startDate;

    @NotNull(message = "Event endDate cannot be null.")
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    private Date endDate;

    @NotNull(message = "Event startTime cannot be null.")
    @Pattern(regexp="^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", message="Invalid time format.")
    private String startTime;

    @NotNull(message = "Event endTime cannot be null.")
    @Pattern(regexp="^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", message="Invalid time format.")
    private String endTime;

    @Valid
    @NotNull(message = "Event location cannot be null.")
    private EventLocationCreateRequest location;

    @NotNull(message = "Event categoryId cannot be null.")
    private Long categoryId;

    public EventUpdateRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public EventLocationCreateRequest getLocation() {
        return location;
    }

    public void setLocation(EventLocationCreateRequest location) {
        this.location = location;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
