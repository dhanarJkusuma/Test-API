package id.dhanarjkusuma.app.loket.service;

import id.dhanarjkusuma.app.loket.domain.Location;
import id.dhanarjkusuma.app.loket.exception.LocationNotFoundException;

import java.util.List;


public interface LocationService {
    Location create(Location location);
    List<Location> fetch();
    Location getLocation(Long id) throws LocationNotFoundException;
}
