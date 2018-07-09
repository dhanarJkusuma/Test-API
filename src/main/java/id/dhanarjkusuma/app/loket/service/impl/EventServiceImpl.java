package id.dhanarjkusuma.app.loket.service.impl;

import id.dhanarjkusuma.app.loket.domain.Category;
import id.dhanarjkusuma.app.loket.domain.Event;
import id.dhanarjkusuma.app.loket.domain.Location;
import id.dhanarjkusuma.app.loket.exception.CategoryNotFoundException;
import id.dhanarjkusuma.app.loket.exception.EventNotFoundException;
import id.dhanarjkusuma.app.loket.repository.EventRepository;
import id.dhanarjkusuma.app.loket.service.CategoryService;
import id.dhanarjkusuma.app.loket.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;
    private CategoryService categoryService;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, CategoryService categoryService) {
        this.eventRepository = eventRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Event create(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public List<Event> fetchAll() {
        return eventRepository.findByIsDeletedFalseByOrderByCreatedAtDesc();
    }

    @Override
    public Event getEvent(String slug) {
        return eventRepository.findBySlugAndIsDeletedFalse(slug).orElseThrow(() -> new EventNotFoundException("Unknown event with slug : " + slug));    }

    @Override
    public Event getEvent(Long id) throws EventNotFoundException {
        return eventRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new EventNotFoundException("Unknown event with id : " + id));
    }

    @Override
    public Event update(Long id, Event event) throws EventNotFoundException, CategoryNotFoundException {
        Event existEvent = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException("Unknown event with id : " + id));
        existEvent.setName(event.getName());
        existEvent.setOrganizerName(event.getOrganizerName());
        existEvent.setDescription(event.getDescription());
        existEvent.setStartDate(event.getStartDate());
        existEvent.setEndDate(event.getEndDate());
        existEvent.setStartTime(event.getStartTime());
        existEvent.setEndTime(event.getEndTime());

        Location location = existEvent.getLocation();
        if(event.getLocation() != null){
            Location requestLocation = event.getLocation();
            location.setName(requestLocation.getName());
            location.setCity(requestLocation.getCity());
            location.setAddress(requestLocation.getAddress());
            location.setLatitude(requestLocation.getLatitude());
            location.setLongitude(requestLocation.getLongitude());
            location.setUpdatedAt(new Date());
            existEvent.setLocation(location);
        }

        Category category = categoryService.getCategory(event.getCategory().getId());
        existEvent.setCategory(category);
        existEvent.setUpdatedAt(new Date());
        return eventRepository.save(existEvent);
    }

    @Override
    public void destroy(Long id) throws EventNotFoundException {
        Event existEvent = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException("Unknown event with id : " + id));
        existEvent.setIsDeleted(true);
        eventRepository.save(existEvent);
    }
}
