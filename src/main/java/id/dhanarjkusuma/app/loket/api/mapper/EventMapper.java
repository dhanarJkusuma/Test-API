package id.dhanarjkusuma.app.loket.api.mapper;

import id.dhanarjkusuma.app.loket.config.MappingConfig;
import id.dhanarjkusuma.app.loket.domain.Category;
import id.dhanarjkusuma.app.loket.domain.Event;
import id.dhanarjkusuma.app.loket.domain.Location;
import id.dhanarjkusuma.app.loket.domain.Ticket;
import id.dhanarjkusuma.app.loket.dtoapi.*;
import id.dhanarjkusuma.app.loket.exception.CategoryNotFoundException;
import id.dhanarjkusuma.app.loket.exception.LocationNotFoundException;
import id.dhanarjkusuma.app.loket.helper.Utils;
import id.dhanarjkusuma.app.loket.service.CategoryService;
import id.dhanarjkusuma.app.loket.service.LocationService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static id.dhanarjkusuma.app.loket.helper.Utils.formatGeneralDate;

@Mapper(config = MappingConfig.class)
public abstract class EventMapper {

    @Autowired
    private LocationService locationService;

    @Autowired
    private CategoryService categoryService;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "thumbnailPhoto", ignore = true)
    @Mapping(target = "organizerName", source = "organizerName")
    @Mapping(target = "organizerPhoto", ignore = true)
    @Mapping(target = "description", source = "description")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "tickets", ignore = true)
    public abstract Event eventCreateRequestToEvent(EventCreateRequest request) throws LocationNotFoundException, CategoryNotFoundException;

    @AfterMapping
    protected void fillRelation(@MappingTarget Event event, EventCreateRequest request){
        Location location = locationService.getLocation(request.getLocationId());
        Category category = categoryService.getCategory(request.getCategoryId());

        event.setLocation(location);
        event.setCategory(category);
        event.setCreatedAt(new Date());
        event.setUpdatedAt(new Date());
    }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "slug", source = "slug")
    @Mapping(target = "thumbnailPhoto", source = "thumbnailPhoto")
    @Mapping(target = "organizerName", source = "organizerName")
    @Mapping(target = "organizerPhoto", source = "organizerPhoto")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "startDate", ignore = true)
    @Mapping(target = "endDate", ignore = true)
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    @Mapping(target = "location", source = "location")
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "categorySlug", ignore = true)
    public abstract EventResponse eventToEventResponse(Event event);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "latitude", source = "latitude")
    @Mapping(target = "longitude", source = "longitude")
    public abstract EventLocationResponse locationToEventLocationResponse(Location location);

    @AfterMapping
    protected void fillDateAndRelation(@MappingTarget EventResponse response, Event event){
        Category category = event.getCategory();
        response.setCategory(category.getName());
        response.setCategorySlug(category.getSlug());

        response.setStartDate(formatGeneralDate(event.getStartDate()));
        response.setEndDate(formatGeneralDate(event.getEndDate()));
    }

    @Mapping(target = "event", source = "event")
    @Mapping(target = "tickets", source = "tickets")
    public abstract EventDetailResponse eventToEventDetailResponse(Event event);

    @Mapping(target = "ticketId", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "quantity", source = "ticketCount")
    @Mapping(target = "actualQuantity", source = "actualTicketCount")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "flag", source = "flag")
    @Mapping(target = "startDate", ignore = true)
    @Mapping(target = "endDate", ignore = true)
    public abstract TicketSummaryResponse ticketToTicketSummaryResponse(Ticket ticket);

    @AfterMapping
    protected void fillDate(@MappingTarget TicketSummaryResponse response, Ticket ticket){
        response.setStartDate(formatGeneralDate(ticket.getStartDate()));
        response.setEndDate(formatGeneralDate(ticket.getEndDate()));
    }
}
