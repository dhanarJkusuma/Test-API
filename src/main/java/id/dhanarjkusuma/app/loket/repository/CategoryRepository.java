package id.dhanarjkusuma.app.loket.repository;

import id.dhanarjkusuma.app.loket.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findBySlug(String slug);
    Optional<Category> findByIdAndIsDeletedFalse(Long id);
    List<Category> findByIsDeletedFalseByOrderByNameAsc();
    Optional<Category> findFirstBySlugAndIsDeletedFalse(String slug);
}
