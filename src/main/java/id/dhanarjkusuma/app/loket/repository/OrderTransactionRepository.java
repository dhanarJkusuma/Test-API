package id.dhanarjkusuma.app.loket.repository;

import id.dhanarjkusuma.app.loket.domain.OrderTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderTransactionRepository extends JpaRepository<OrderTransaction, Long> {
    Optional<OrderTransaction> findFirstByPublicId(String publicId);
}
