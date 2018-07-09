package id.dhanarjkusuma.app.loket.service;

import id.dhanarjkusuma.app.loket.domain.Event;
import id.dhanarjkusuma.app.loket.exception.CategoryNotFoundException;
import id.dhanarjkusuma.app.loket.exception.EventNotFoundException;

import java.util.List;

public interface EventService {
    Event create(Event event);
    List<Event> fetchAll();
    Event getEvent(String slug);
    Event getEvent(Long id) throws EventNotFoundException;
    Event update(Long id, Event event) throws EventNotFoundException, CategoryNotFoundException;
    void destroy(Long id) throws EventNotFoundException;
}
