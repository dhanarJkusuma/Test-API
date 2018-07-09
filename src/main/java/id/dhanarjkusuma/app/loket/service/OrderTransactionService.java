package id.dhanarjkusuma.app.loket.service;

import id.dhanarjkusuma.app.loket.domain.OrderTransaction;
import id.dhanarjkusuma.app.loket.dtoservice.Checkout;
import id.dhanarjkusuma.app.loket.exception.OrderTransactionNotFoundException;

public interface OrderTransactionService {
    OrderTransaction create(Checkout checkout);
    OrderTransaction getTransaction(Long id) throws OrderTransactionNotFoundException;
}
