package id.dhanarjkusuma.app.loket.repository;

import id.dhanarjkusuma.app.loket.domain.OrderTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderTransactionRepository extends JpaRepository<OrderTransaction, Long> {
}
