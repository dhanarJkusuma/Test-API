package id.dhanarjkusuma.app.loket.service.impl;

import id.dhanarjkusuma.app.loket.domain.Category;
import id.dhanarjkusuma.app.loket.domain.Event;
import id.dhanarjkusuma.app.loket.domain.Location;
import id.dhanarjkusuma.app.loket.domain.specification.GeneralSpecificationBuilder;
import id.dhanarjkusuma.app.loket.dtoservice.EventFilter;
import id.dhanarjkusuma.app.loket.exception.CategoryNotFoundException;
import id.dhanarjkusuma.app.loket.exception.EventNotFoundException;
import id.dhanarjkusuma.app.loket.exception.StorageErrorException;
import id.dhanarjkusuma.app.loket.helper.Utils;
import id.dhanarjkusuma.app.loket.repository.EventRepository;
import id.dhanarjkusuma.app.loket.service.CategoryService;
import id.dhanarjkusuma.app.loket.service.EventService;
import id.dhanarjkusuma.app.loket.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static id.dhanarjkusuma.app.loket.helper.Utils.toSlug;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;
    private CategoryService categoryService;
    private StorageService storageService;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, CategoryService categoryService, StorageService storageService) {
        this.eventRepository = eventRepository;
        this.categoryService = categoryService;
        this.storageService = storageService;
    }

    @Override
    public Event create(Event event) {
        event.setSlug(getUniqueSlug(toSlug(event.getName())));
        return eventRepository.save(event);
    }

    @Override
    public List<Event> fetchAll() {
        return eventRepository.findByIsDeletedFalseOrderByCreatedAtDesc();
    }

    @Override
    public Page<Event> filterAllData(@NotNull EventFilter e, int page, int size) {
        GeneralSpecificationBuilder<Event> specificationBuilder = new GeneralSpecificationBuilder<>();
        if(e.getSearchName() != null){
            specificationBuilder.with("name", "LIKE", e.getSearchName());
        }
        if(e.getCity() != null){
            specificationBuilder.with("location.city", ":", e.getCity());
        }
        if(e.getSlugCategory() != null){
            specificationBuilder.with("category.slug", ":", e.getSlugCategory());
        }
        if(e.getFilterDate() != null){
            specificationBuilder.with("startDate", "<=", e.getFilterDate());
            specificationBuilder.with("endDate", ">=", e.getFilterDate());
        }
        Specification<Event> specification = specificationBuilder.build();
        Pageable pageRequest = PageRequest.of(page - 1, size);
        return eventRepository.findAll(specification, pageRequest);
    }

    @Override
    public Event uploadThumbnail(Long id, MultipartFile multipartFile) throws EventNotFoundException, StorageErrorException {
        Event existEvent = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException("Unknown event with id : " + id));
        Path path = storageService.store(multipartFile, "/thumbnail", existEvent.getSlug(), false);
        existEvent.setThumbnailPhoto(path.toString());
        return eventRepository.save(existEvent);
    }

    @Override
    public Event uploadOrganizer(Long id, MultipartFile multipartFile) throws EventNotFoundException, StorageErrorException {
        Event existEvent = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException("Unknown event with id : " + id));
        String organizerSlug = toSlug(existEvent.getOrganizerName());
        Path path = storageService.store(multipartFile, "/organizer", existEvent.getSlug() + "_" + organizerSlug, false);
        existEvent.setOrganizerPhoto(path.toString());
        return eventRepository.save(existEvent);
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

    private String getUniqueSlug(String slug){
        Optional<Event> event = eventRepository.findBySlugAndIsDeletedFalse(slug);
        if(!event.isPresent()){
            return slug;
        }
        Date now = new Date();
        return String.join("-", slug, String.valueOf(now.hashCode()));
    }
}
