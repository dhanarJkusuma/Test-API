package id.dhanarjkusuma.app.loket.repository;

import id.dhanarjkusuma.app.loket.domain.Event;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    List<Event> findByIsDeletedFalseOrderByCreatedAtDesc();
    Optional<Event> findByIdAndIsDeletedFalse(Long id);
    Optional<Event> findBySlugAndIsDeletedFalse(String slug);
}
