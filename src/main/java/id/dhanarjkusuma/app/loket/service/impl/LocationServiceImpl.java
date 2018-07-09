package id.dhanarjkusuma.app.loket.service.impl;

import id.dhanarjkusuma.app.loket.domain.Location;
import id.dhanarjkusuma.app.loket.exception.LocationNotFoundException;
import id.dhanarjkusuma.app.loket.repository.LocationRepository;
import id.dhanarjkusuma.app.loket.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public Location create(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public List<Location> fetch() {
        return locationRepository.findByIsDeletedFalseByOrderByCreatedAtDesc();
    }

    @Override
    public Location getLocation(Long id) throws LocationNotFoundException {
        return locationRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new LocationNotFoundException("Unknown Location with id : " + id));
    }
}
