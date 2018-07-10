package id.dhanarjkusuma.app.loket.api;

import id.dhanarjkusuma.app.loket.api.mapper.OrderTransactionMapper;
import id.dhanarjkusuma.app.loket.domain.OrderTransaction;
import id.dhanarjkusuma.app.loket.dtoapi.*;
import id.dhanarjkusuma.app.loket.dtoservice.Checkout;
import id.dhanarjkusuma.app.loket.exception.InvalidCheckoutException;
import id.dhanarjkusuma.app.loket.exception.OrderTransactionNotFoundException;
import id.dhanarjkusuma.app.loket.service.OrderTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/transaction")
public class OrderTransactionApi {

    private OrderTransactionService orderTransactionService;
    private OrderTransactionMapper mapper;

    @Autowired
    public OrderTransactionApi(OrderTransactionService orderTransactionService, OrderTransactionMapper mapper) {
        this.orderTransactionService = orderTransactionService;
        this.mapper = mapper;
    }

    @PostMapping(path = "/purchase")
    public OrderTransactionResponse purchase(@Valid @RequestBody CheckoutRequest request){
        Checkout checkout = mapper.checkoutRequestToCheckout(request);
        OrderTransaction savedTransaction = orderTransactionService.create(checkout);
        return mapper.orderTransactionToOrderTransactionResponse(savedTransaction);
    }

    @GetMapping(path = "/get_info")
    public OrderTransactionPagination getTransactionPagination(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        Page<OrderTransaction> orderTransactions = orderTransactionService.retrieveTransactions(page, size);
        return OrderTransactionPagination
                .Builder
                .newBuilder()
                .content(
                        orderTransactions.getContent()
                        .stream()
                        .map(mapper::orderTransactionToOrderTransactionResponse)
                        .collect(Collectors.toList())
                )
                .currentPage(orderTransactions.getNumber() + 1)
                .pageSize(orderTransactions.getNumberOfElements())
                .totalItem(orderTransactions.getTotalElements())
                .build();

    }

    @GetMapping(path = "/get_detail")
    public OrderTransactionResponse getTransaction(@RequestParam(name = "invoiceNo") String invoiceNo){
        OrderTransaction orderTransaction = orderTransactionService.getTransactionByPublicId(invoiceNo);
        return mapper.orderTransactionToOrderTransactionResponse(orderTransaction);
    }

    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public ErrorResponseMessage handleNotFoundException(OrderTransactionNotFoundException e){
        return new ErrorResponseMessage(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponseRequest handleCheckoutErr(InvalidCheckoutException e){
        ErrorResponseRequest response = new ErrorResponseRequest();
        response.setMessage("Error while Checkout transaction");
        response.setErrors(e.getFieldsError());
        return response;
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponseRequest handleException(MethodArgumentNotValidException exception) {
        List<FieldError> errors = exception.getBindingResult().getFieldErrors();
        Map<String, List<String>> errorFields = new HashMap<>();
        for(FieldError fieldError: errors){
            List<String> errMessages = errorFields.get(fieldError.getField());
            if(errMessages == null){
                errMessages = new ArrayList<>();
            }
            errMessages.add(fieldError.getDefaultMessage());
            errorFields.put(fieldError.getField(), errMessages);
        }
        ErrorResponseRequest response = new ErrorResponseRequest();
        response.setMessage("Error while Checkout transaction");
        response.setErrors(errorFields);
        return response;
    }
}

