package by.clevertec.project.domain.order_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static by.clevertec.lib.builder.lexer.utility.StringUtility.ls;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Customer {
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate dateBirth;
    private List<Order> orders;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id + ls +
                ", firstName='" + firstName + '\'' + ls +
                ", lastName='" + lastName + '\'' + ls +
                ", dateBirth=" + dateBirth + ls +
                ", orders=" + buildStringFromOrders() + ls +
                '}';
    }

    private String buildStringFromOrders() {
        StringBuilder sb = new StringBuilder();
        if (orders != null) {
            for (Order order : orders) {
                sb.append(order.toString());
                sb.append(ls);
            }
        } else {
            sb.append("null");
        }
        return sb.toString();
    }
}
