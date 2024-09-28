package by.clevertec.project.domain.order_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import static by.clevertec.lib.builder.lexer.utility.StringUtility.ls;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Product {
    private UUID id;
    private String name;
    private Double price;
    private Map<UUID, BigDecimal> priceMap;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id + ls +
                ", name='" + name + '\'' + ls +
                ", price=" + price + ls +
                ", priceMap=" + buildStringFromPriceMap() + ls +
                '}';
    }

    private String buildStringFromPriceMap() {
        StringBuilder sb = new StringBuilder();
        if (priceMap != null) {
            for (Map.Entry<UUID, BigDecimal> pair : priceMap.entrySet()) {
                sb.append(pair.getKey());
                sb.append(" : ");
                sb.append(pair.getValue());
                sb.append(ls);
            }
        } else {
            sb.append("null");
        }
        return sb.toString();
    }
}
