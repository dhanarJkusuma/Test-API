package id.dhanarjkusuma.app.loket.api;

import id.dhanarjkusuma.app.loket.api.mapper.TicketMapper;
import id.dhanarjkusuma.app.loket.domain.Ticket;
import id.dhanarjkusuma.app.loket.dtoapi.ErrorResponseMessage;
import id.dhanarjkusuma.app.loket.dtoapi.ErrorResponseRequest;
import id.dhanarjkusuma.app.loket.dtoapi.TicketCreateRequest;
import id.dhanarjkusuma.app.loket.dtoapi.TicketResponse;
import id.dhanarjkusuma.app.loket.exception.CategoryNotFoundException;
import id.dhanarjkusuma.app.loket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/event")
public class TicketApi {

    private TicketService ticketService;
    private TicketMapper mapper;

    @Autowired
    public TicketApi(TicketService ticketService, TicketMapper mapper) {
        this.ticketService = ticketService;
        this.mapper = mapper;
    }

    @PostMapping(path = "/ticket/create")
    public TicketResponse create(@Valid @RequestBody TicketCreateRequest request){
        Ticket ticket = mapper.ticketCreateRequestToTicket(request);
        return mapper.ticketToTicketResponse(ticketService.create(ticket));
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseMessage handleNotFoundException(CategoryNotFoundException e){
        return new ErrorResponseMessage(e.getMessage());
    }


    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponseRequest handleException(MethodArgumentNotValidException exception) {
        List<FieldError> errors = exception.getBindingResult().getFieldErrors();
        Map<String, List<String>> errorFields = new HashMap<>();
        for(FieldError fieldError: errors){
            List<String> errMessages = errorFields.get(fieldError.getField());
            if(errMessages == null){
                errMessages = new ArrayList<>();
            }
            errMessages.add(fieldError.getDefaultMessage());
            errorFields.put(fieldError.getField(), errMessages);
        }
        ErrorResponseRequest response = new ErrorResponseRequest();
        response.setMessage("Error while Creating/Updating a location");
        response.setErrors(errorFields);
        return response;
    }

}
