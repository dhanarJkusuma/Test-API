package id.dhanarjkusuma.app.loket.api;

import id.dhanarjkusuma.app.loket.api.mapper.EventMapper;
import id.dhanarjkusuma.app.loket.domain.Event;
import id.dhanarjkusuma.app.loket.dtoapi.*;
import id.dhanarjkusuma.app.loket.dtoservice.EventFilter;
import id.dhanarjkusuma.app.loket.exception.CategoryNotFoundException;
import id.dhanarjkusuma.app.loket.exception.EventNotFoundException;
import id.dhanarjkusuma.app.loket.exception.LocationNotFoundException;

import id.dhanarjkusuma.app.loket.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

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
    public EventPagination filterEvent(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "search_name", required = false) @Nullable String name,
            @RequestParam(name = "search_date", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") @Nullable Date filterDate,
            @RequestParam(name = "city", required = false) @Nullable String city,
            @RequestParam(name = "category", required = false) @Nullable String category
    ){
        EventFilter eventFilter = EventFilter.createFilter(name, category, city, filterDate);
        Page<Event> filteredEvent = eventService.filterAllData(eventFilter, page, size);
        return EventPagination.Builder
                .newBuilder()
                .content(
                        filteredEvent.stream()
                        .map(mapper::eventToEventResponse)
                        .collect(Collectors.toList())
                )
                .currentPage(filteredEvent.getNumber() + 1)
                .pageSize(filteredEvent.getNumberOfElements())
                .totalItem(filteredEvent.getTotalElements())
                .build();

    }

    @PostMapping(path = "/upload_thumbnail")
    public EventResponse uploadThumbnail(
            @RequestParam(name = "slug") String slug,
            @RequestParam(value = "image",required = false) MultipartFile image
    ){
        Event event = eventService.getEvent(slug);
        Event savedEvent = eventService.uploadThumbnail(event.getId(), image);
        return mapper.eventToEventResponse(savedEvent);
    }

    @PostMapping(path = "/upload_organizer")
    public EventResponse uploadOrganizerPhoto(
            @RequestParam(name = "slug") String slug,
            @RequestParam(value = "image",required = false) MultipartFile image
    ){
        Event event = eventService.getEvent(slug);
        Event savedEvent = eventService.uploadOrganizer(event.getId(), image);
        return mapper.eventToEventResponse(savedEvent);
    }

    @GetMapping(path = "/get_detail")
    public EventDetailResponse getEvent(@RequestParam(name = "slug") String slug){
        Event event = eventService.getEvent(slug);
        return mapper.eventToEventDetailResponse(event);
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
