package id.dhanarjkusuma.app.loket.api.mapper;

import id.dhanarjkusuma.app.loket.config.MappingConfig;
import id.dhanarjkusuma.app.loket.domain.OrderTransaction;
import id.dhanarjkusuma.app.loket.domain.OrderTransactionItem;
import id.dhanarjkusuma.app.loket.dtoapi.*;
import id.dhanarjkusuma.app.loket.dtoservice.Checkout;
import id.dhanarjkusuma.app.loket.dtoservice.CheckoutCustomer;
import id.dhanarjkusuma.app.loket.dtoservice.CheckoutItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static id.dhanarjkusuma.app.loket.helper.Utils.formatGeneralDate;
import static id.dhanarjkusuma.app.loket.helper.Utils.formatGeneralDateTime;

@Mapper(config = MappingConfig.class, uses = { EventMapper.class })
public abstract class OrderTransactionMapper {


    @Mapping(target = "invoiceNo", source = "publicId")
    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "event", source = "event")
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "items", source = "items")
    public abstract OrderTransactionResponse orderTransactionToOrderTransactionResponse(OrderTransaction orderTransaction);

    @Mapping(target = "ticketName", source = "ticket.name")
    @Mapping(target = "ticketFlag", source = "ticket.flag")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "totalPrice", source = "totalPrice")
    public abstract OrderTransactionItemResponse orderTransactionItemToOrderTransactionItemResponse(OrderTransactionItem item);

    @AfterMapping
    protected void fillDateAndCustomer(@MappingTarget OrderTransactionResponse response, OrderTransaction orderTransaction){
        response.setOrderDate(formatGeneralDateTime(orderTransaction.getOrderDate()));
        OrderTransactionCustomerResponse customerResponse = new OrderTransactionCustomerResponse();
        customerResponse.setFirstName(orderTransaction.getCustomerFirstName());
        customerResponse.setLastName(orderTransaction.getCustomerLastName());
        customerResponse.setDateOfBirth(formatGeneralDate(orderTransaction.getCustomerDateOfBirth()));
        customerResponse.setGender(orderTransaction.getCustomerGender().name());
        customerResponse.setPhone(orderTransaction.getCustomerPhone());
        customerResponse.setEmail(orderTransaction.getCustomerEmail());
        response.setCustomer(customerResponse);
    }

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "dateOfBirth", source = "dateOfBirth")
    @Mapping(target = "gender", source = "gender")
    public abstract CheckoutCustomer checkoutCustomerRequestToCheckoutCustomer(CheckoutCustomerRequest request);

    @Mapping(target = "ticketId", source = "ticketId")
    @Mapping(target = "quantity", source = "quantity")
    public abstract CheckoutItem checkoutItemRequestToCheckoutItem(CheckoutItemRequest request);

    @Mapping(target = "customer", source = "customer")
    @Mapping(target = "eventId", source = "eventId")
    @Mapping(target = "items", source = "items")
    public abstract Checkout checkoutRequestToCheckout(CheckoutRequest request);
}
