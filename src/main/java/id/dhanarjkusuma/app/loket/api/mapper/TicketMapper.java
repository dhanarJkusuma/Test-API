package id.dhanarjkusuma.app.loket.api.mapper;

import id.dhanarjkusuma.app.loket.config.MappingConfig;
import id.dhanarjkusuma.app.loket.domain.Event;
import id.dhanarjkusuma.app.loket.domain.Ticket;
import id.dhanarjkusuma.app.loket.domain.TicketFlag;
import id.dhanarjkusuma.app.loket.dtoapi.TicketCreateRequest;
import id.dhanarjkusuma.app.loket.dtoapi.TicketResponse;
import id.dhanarjkusuma.app.loket.service.EventService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

import static id.dhanarjkusuma.app.loket.domain.TicketFlag.FREE;
import static id.dhanarjkusuma.app.loket.helper.Utils.formatGeneralDate;

@Mapper(config = MappingConfig.class, uses = { EventMapper.class })
public abstract class TicketMapper {

    @Autowired
    private EventService eventService;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", source = "price")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "event", ignore = true)
    @Mapping(target = "ticketCount", source = "quantity")
    @Mapping(target = "actualTicketCount", source = "quantity")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "flag", source = "flag")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    public abstract Ticket ticketCreateRequestToTicket(TicketCreateRequest request);

    @AfterMapping
    protected void fillEventAndDate(@MappingTarget Ticket ticket, TicketCreateRequest request){
        Event event = eventService.getEvent(request.getEventId());
        ticket.setEvent(event);

        ticket.setCreatedAt(new Date());
        ticket.setUpdatedAt(new Date());
        if(ticket.getFlag().equals(FREE)){
            ticket.setPrice(BigDecimal.ZERO);
        }
    }

    @Mapping(target = "event", source = "event")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "quantity", source = "ticketCount")
    @Mapping(target = "actualQuantity", source = "actualTicketCount")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "flag", source = "flag")
    @Mapping(target = "startDate", ignore = true)
    @Mapping(target = "endDate", ignore = true)
    public abstract TicketResponse ticketToTicketResponse(Ticket ticket);

    @AfterMapping
    protected void fillDate(@MappingTarget TicketResponse response, Ticket ticket){
        response.setStartDate(formatGeneralDate(ticket.getStartDate()));
        response.setEndDate(formatGeneralDate(ticket.getEndDate()));
    }
}
