package by.clevertec.project.convertor.order_service;

import by.clevertec.lib.api.AJConvertor;
import by.clevertec.lib.api.AJConvertorContext;
import by.clevertec.lib.intermediate_representation.AJArray;
import by.clevertec.lib.intermediate_representation.AJElement;
import by.clevertec.lib.intermediate_representation.AJObject;
import by.clevertec.project.domain.order_service.Customer;
import by.clevertec.project.domain.order_service.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerConvertor implements AJConvertor<Customer> {
    @Override
    public Class<Customer> forType() {
        return Customer.class;
    }

    @Override
    public AJObject toAJElement(Customer customer, AJConvertorContext context) {
        AJObject object = new AJObject();
        object.addProperty("id", customer.getId().toString());
        object.addProperty("firstName", customer.getFirstName());
        object.addProperty("lastName", customer.getLastName());
        object.addProperty("dateBirth", customer.getDateBirth().toString());

        if (customer.getOrders() != null) {
            AJArray orders = new AJArray();
            object.addProperty("orders", orders);
            for (Order order : customer.getOrders()) {
                orders.add(context.toAJElement(order));
            }
        }
        return object;
    }

    @Override
    public Customer toUserType(AJElement element, AJConvertorContext context) {
        AJObject object = expectedObject(element);
        Customer customer = new Customer();
        customer.setId(UUID.fromString(object.getAsString("id")));
        customer.setFirstName(object.getAsString("firstName"));
        customer.setLastName(object.getAsString("lastName"));
        customer.setDateBirth(LocalDate.parse(object.getAsString("dateBirth")));

        var ordersArray = object.getAsArray("orders");
        if(ordersArray != null) {
            List<Order> orders = new ArrayList<>();
            customer.setOrders(orders);
            for (AJElement item : ordersArray.getAll()) {
                orders.add(context.toUserType(Order.class, (AJObject) item));
            }
        }
        return customer;
    }
}
