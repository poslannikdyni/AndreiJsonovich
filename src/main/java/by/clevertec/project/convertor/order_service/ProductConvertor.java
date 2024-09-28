package by.clevertec.project.convertor.order_service;

import by.clevertec.lib.api.AJConvertor;
import by.clevertec.lib.api.AJConvertorContext;
import by.clevertec.lib.intermediate_representation.AJArray;
import by.clevertec.lib.intermediate_representation.AJElement;
import by.clevertec.lib.intermediate_representation.AJObject;
import by.clevertec.lib.intermediate_representation.AJPrimitive;
import by.clevertec.project.domain.order_service.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProductConvertor implements AJConvertor<Product> {
    @Override
    public Class<Product> forType() {
        return Product.class;
    }

    @Override
    public AJObject toAJObject(Product product, AJConvertorContext context) {
        AJObject object = new AJObject();
        object.addProperty("id", product.getId().toString());
        object.addProperty("name", product.getName());
        object.addProperty("price", product.getPrice().toString());

        if (product.getPriceMap() != null) {
            AJArray priceMap = new AJArray();
            object.addProperty("priceMap", priceMap);
            if (product.getPriceMap() != null) {
                for (Map.Entry<UUID, BigDecimal> pair : product.getPriceMap().entrySet()) {
                    priceMap.add(buildArrayFromPriceMapEntry(pair));
                }
            }
        }
        return object;
    }

    private AJArray buildArrayFromPriceMapEntry(Map.Entry<UUID, BigDecimal> pair) {
        AJArray rez = new AJArray();
        rez.add(
                new AJPrimitive(
                        AJPrimitive.ContentPresentation.STRING,
                        pair.getKey().toString()
                )
        );
        rez.add(
                new AJPrimitive(
                        AJPrimitive.ContentPresentation.STRING,
                        pair.getValue().toString()
                )
        );
        return rez;
    }

    @Override
    public Product toUserType(AJObject object, AJConvertorContext context) {
        Product product = new Product();
        product.setId(UUID.fromString(object.getAsString("id")));
        product.setName(object.getAsString("name"));
        product.setPrice(Double.parseDouble(object.getAsString("price")));

        var pricaMapArray = object.getAsArray("priceMap");
        if(pricaMapArray != null) {
            Map<UUID, BigDecimal> priceMap = new HashMap<>();
            product.setPriceMap(priceMap);
            var priceMapFromObject = object.getAsArray("priceMap");
            if (priceMapFromObject != null) {
                for (AJElement arrayItem : priceMapFromObject.getAll()) {
                    var mapEntryArray = (AJArray) arrayItem;
                    var key = ((AJPrimitive) mapEntryArray.getAll().get(0)).getContent().toString();
                    var value = ((AJPrimitive) mapEntryArray.getAll().get(1)).getContent().toString();
                    priceMap.put(UUID.fromString(key), new BigDecimal(value));
                }
            }
        }
        return product;
    }
}
