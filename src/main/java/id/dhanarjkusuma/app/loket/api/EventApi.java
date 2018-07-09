package id.dhanarjkusuma.app.loket.api;

import id.dhanarjkusuma.app.loket.api.mapper.EventMapper;
import id.dhanarjkusuma.app.loket.domain.Event;
import id.dhanarjkusuma.app.loket.dtoapi.ErrorResponseMessage;
import id.dhanarjkusuma.app.loket.dtoapi.ErrorResponseRequest;
import id.dhanarjkusuma.app.loket.dtoapi.EventCreateRequest;
import id.dhanarjkusuma.app.loket.dtoapi.EventResponse;
import id.dhanarjkusuma.app.loket.exception.CategoryNotFoundException;
import id.dhanarjkusuma.app.loket.exception.EventNotFoundException;
import id.dhanarjkusuma.app.loket.exception.LocationNotFoundException;

import id.dhanarjkusuma.app.loket.service.EventService;
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
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/event")
public class EventApi {


    private EventService eventService;
    private EventMapper mapper;

    @Autowired
    public EventApi(EventService eventService, EventMapper mapper) {
        this.eventService = eventService;
        this.mapper = mapper;
    }

    @PostMapping(path = "/create")
    public EventResponse createEvent(@Valid @RequestBody EventCreateRequest request) {
        Event event = mapper.eventCreateRequestToEvent(request);
        return mapper.eventToEventResponse(eventService.create(event));
    }

    @GetMapping(path = "/get_info")
    public EventResponse getEvent(@PathVariable(name = "slug") String slug){
        Event event = eventService.getEvent(slug);
        return mapper.eventToEventResponse(event);
    }

    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public ErrorResponseMessage handleLocation(LocationNotFoundException e){
        return new ErrorResponseMessage(e.getMessage());
    }



    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseMessage handleNotFoundException(EventNotFoundException e){
        return new ErrorResponseMessage(e.getMessage());
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
        response.setMessage("Error while Creating/Updating an event");
        response.setErrors(errorFields);
        return response;
    }
}
