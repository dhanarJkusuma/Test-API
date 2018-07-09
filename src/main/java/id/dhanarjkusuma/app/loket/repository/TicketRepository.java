package id.dhanarjkusuma.app.loket.repository;

import id.dhanarjkusuma.app.loket.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
