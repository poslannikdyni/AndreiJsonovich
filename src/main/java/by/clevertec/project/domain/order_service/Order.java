package by.clevertec.project.domain.order_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static by.clevertec.lib.builder.lexer.utility.StringUtility.ls;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Order {
    private UUID id;
    private List<Product> products;
    private OffsetDateTime createDate;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id + ls +
                ", products=" + buildStringFromProducts() + ls +
                ", createDate=" + createDate + ls +
                '}';
    }

    private String buildStringFromProducts() {
        StringBuilder sb = new StringBuilder();
        if (products != null) {
            for (Product product : products) {
                sb.append(product.toString());
                sb.append(ls);
            }
        } else {
            sb.append("null");
        }
        return sb.toString();
    }
}
