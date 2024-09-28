package by.clevertec.project.convertor.order_service;

import by.clevertec.lib.api.AJConvertor;
import by.clevertec.lib.api.AJConvertorContext;
import by.clevertec.lib.intermediate_representation.AJArray;
import by.clevertec.lib.intermediate_representation.AJElement;
import by.clevertec.lib.intermediate_representation.AJObject;
import by.clevertec.project.domain.order_service.Order;
import by.clevertec.project.domain.order_service.Product;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderConvertor implements AJConvertor<Order> {
    @Override
    public Class<Order> forType() {
        return Order.class;
    }

    @Override
    public AJObject toAJObject(Order order, AJConvertorContext context) {
        AJObject object = new AJObject();
        object.addProperty("id", order.getId().toString());

        if(order.getProducts() != null) {
            AJArray products = new AJArray();
            object.addProperty("products", products);
            for (Product product : order.getProducts()) {
                products.add(context.toAJObject(product));
            }
        }

        object.addProperty("createDate", order.getCreateDate().toString());
        return object;
    }

    @Override
    public Order toUserType(AJObject object, AJConvertorContext context) {
        Order order = new Order();
        order.setId(UUID.fromString(object.getAsString("id")));
        order.setCreateDate(OffsetDateTime.parse(object.getAsString("createDate")));

        var productsArray = object.getAsArray("products");
        if(productsArray != null) {
            List<Product> products = new ArrayList<>();
            order.setProducts(products);
            for (AJElement item : productsArray.getAll()) {
                products.add(context.toUserType(Product.class, (AJObject) item));
            }
        }
        return order;
    }
}
