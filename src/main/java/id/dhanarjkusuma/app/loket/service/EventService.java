package id.dhanarjkusuma.app.loket.service;

import id.dhanarjkusuma.app.loket.domain.Event;
import id.dhanarjkusuma.app.loket.dtoservice.EventFilter;
import id.dhanarjkusuma.app.loket.exception.CategoryNotFoundException;
import id.dhanarjkusuma.app.loket.exception.EventNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EventService {
    Event create(Event event);
    List<Event> fetchAll();
    Page<Event> filterAllData(EventFilter e, int page, int size);
    Event uploadThumbnail(Long id, MultipartFile multipartFile) throws EventNotFoundException;
    Event uploadOrganizer(Long id, MultipartFile multipartFile) throws EventNotFoundException;
    Event getEvent(String slug);
    Event getEvent(Long id) throws EventNotFoundException;
    Event update(Long id, Event event) throws EventNotFoundException, CategoryNotFoundException;
    void destroy(Long id) throws EventNotFoundException;
}
