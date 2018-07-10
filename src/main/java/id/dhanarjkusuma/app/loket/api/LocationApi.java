package id.dhanarjkusuma.app.loket.api;


import id.dhanarjkusuma.app.loket.api.mapper.LocationMapper;
import id.dhanarjkusuma.app.loket.domain.Location;
import id.dhanarjkusuma.app.loket.dtoapi.ErrorResponseRequest;
import id.dhanarjkusuma.app.loket.dtoapi.LocationCreateRequest;
import id.dhanarjkusuma.app.loket.dtoapi.LocationResponse;
import id.dhanarjkusuma.app.loket.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


@RestController
@RequestMapping("/location")
public class LocationApi {

    private LocationService locationService;
    private LocationMapper mapper;

    @Autowired
    public LocationApi(LocationService locationService, LocationMapper mapper) {
        this.locationService = locationService;
        this.mapper = mapper;
    }

    @PostMapping(path = "/create")
    public LocationResponse create(@Valid @RequestBody LocationCreateRequest request){
        Location location = mapper.locationRequestToLocation(request);
        return mapper.locationToLocationResponse(locationService.create(location));
    }

    @GetMapping(path = "/get_info")
    public List<LocationResponse> fetch(){
        List<Location> locationList = locationService.fetch();
        return locationList
                .stream()
                .map(mapper::locationToLocationResponse)
                .collect(Collectors.toList());
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
