package id.dhanarjkusuma.app.loket.repository;

import id.dhanarjkusuma.app.loket.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByIsDeletedFalseOrderByCreatedAtDesc();
    Optional<Location> findByIdAndIsDeletedFalse(Long id);
}
