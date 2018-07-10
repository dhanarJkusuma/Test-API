package id.dhanarjkusuma.app.loket.service;

import id.dhanarjkusuma.app.loket.domain.OrderTransaction;
import id.dhanarjkusuma.app.loket.dtoservice.Checkout;
import id.dhanarjkusuma.app.loket.exception.OrderTransactionNotFoundException;
import org.springframework.data.domain.Page;

public interface OrderTransactionService {
    OrderTransaction create(Checkout checkout);
    OrderTransaction getTransaction(Long id) throws OrderTransactionNotFoundException;
    OrderTransaction getTransactionByPublicId(String publicId) throws OrderTransactionNotFoundException;
    Page<OrderTransaction> retrieveTransactions(int page, int size);
}
