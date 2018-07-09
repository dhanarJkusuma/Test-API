package id.dhanarjkusuma.app.loket.api.mapper;

import id.dhanarjkusuma.app.loket.config.MappingConfig;
import id.dhanarjkusuma.app.loket.domain.Event;
import id.dhanarjkusuma.app.loket.domain.Ticket;
import id.dhanarjkusuma.app.loket.dtoapi.TicketCreateRequest;
import id.dhanarjkusuma.app.loket.dtoapi.TicketResponse;
import id.dhanarjkusuma.app.loket.service.EventService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import static id.dhanarjkusuma.app.loket.helper.Utils.formatGeneralDate;

@Mapper(config = MappingConfig.class, imports = { EventMapper.class })
public abstract class TicketMapper {

    @Autowired
    private EventService eventService;

    @Mapping(target = "name", source = "name")
    @Mapping(target = "event", ignore = true)
    @Mapping(target = "ticketCount", source = "quantity")
    @Mapping(target = "actualTicketCount", source = "actualQuantity")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "flag", source = "flag")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    public abstract Ticket ticketCreateRequestToTicket(TicketCreateRequest request);

    @AfterMapping
    protected void fillEventAndDate(@MappingTarget Ticket ticket, TicketCreateRequest request){
        Event event = eventService.getEvent(request.getEventId());
        ticket.setEvent(event);
    }

    @Mapping(target = "event", source = "event")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "actualQuantity", source = "actualQuantity")
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
