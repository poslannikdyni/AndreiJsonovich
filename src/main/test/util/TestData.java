package util;

import by.clevertec.project.domain.order_service.Customer;
import by.clevertec.project.domain.order_service.Order;
import by.clevertec.project.domain.order_service.Product;
import by.clevertec.project.domain.simple.A;
import by.clevertec.project.domain.simple.B;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class TestData {
    public static A getSimpleServiceTestData() {
        return new A(10,
                List.of(
                        new B(true, "Element 1"),
                        new B(true, "Element 2"),
                        new B(false, "Element 3")
                ),
                Set.of(
                        new B(false, "Element 4")
                ),
                Map.of(
                        1, new B(false, "Element 5"),
                        2, new B(false, "Element 6"),
                        3, new B(false, "Element 7")
                )
        );
    }

    public static Customer getOrderServiceTestCustomer() {
        return new Customer(
                UUID.fromString("d3a3fb3f-1111-4981-88b5-bbea21c5f8ac"),
                "CustomerName",
                "CustomerLastName",
                LocalDate.of(1995, 02, 28),
                buildOrdersList()
        );
    }

    private static List<Order> buildOrdersList() {
        return List.of(
                new Order(UUID.fromString("d3a3fb3f-2222-4981-88b5-bbea21c5f8ac"),
                        buildProductList1(),
                        OffsetDateTime.parse("2024-09-27T15:21:23.210398200+03:00")
                ),
                new Order(UUID.fromString("d3a3fb3f-3333-4981-88b5-bbea21c5f8ac"),
                        buildProductList2(),
                        OffsetDateTime.parse("2024-09-27T15:21:23.210398200+03:00")
                ),
                new Order(UUID.fromString("d3a3fb3f-4444-4981-88b5-bbea21c5f8ac"),
                        buildProductList3(),
                        OffsetDateTime.parse("2024-09-27T15:21:23.210398200+03:00")
                )
        );
    }

    private static List<Product> buildProductList1() {
        return List.of(
                new Product(UUID.fromString("d3a3fb3f-5555-4981-88b5-bbea21c5f8ac"),
                        "Product1",
                        1.0d,
                        new HashMap<>()
                )
        );
    }

    private static List<Product> buildProductList2() {
        return List.of(
                new Product(UUID.fromString("d3a3fb3f-6666-4981-88b5-bbea21c5f8ac"),
                        "Product2.1",
                        2.1d,
                        new HashMap<>()
                ),
                new Product(UUID.fromString("d3a3fb3f-7777-4981-88b5-bbea21c5f8ac"),
                        "Product2.2",
                        2.2d,
                        new HashMap<>()
                )
        );
    }

    private static List<Product> buildProductList3() {
        return List.of(
                new Product(UUID.fromString("d3a3fb3f-8888-4981-88b5-bbea21c5f8ac"),
                        "Product3.1",
                        3.1d,
                        Map.of(
                                UUID.fromString("d3a3fb3f-8888-1111-88b5-bbea21c5f8ac"), BigDecimal.valueOf(3.1001d)
                        )
                ),
                new Product(UUID.fromString("d3a3fb3f-9999-4981-88b5-bbea21c5f8ac"),
                        "Product3.2",
                        3.2d,
                        Map.of(
                                UUID.fromString("d3a3fb3f-9999-1111-88b5-bbea21c5f8ac"), BigDecimal.valueOf(3.2001d),
                                UUID.fromString("d3a3fb3f-9999-1112-88b5-bbea21c5f8ac"), BigDecimal.valueOf(3.2002d)
                        )
                ),
                new Product(UUID.fromString("d3a3fb3f-0000-4981-88b5-bbea21c5f8ac"),
                        "Product3.3",
                        3.3d,
                        Map.of(
                                UUID.fromString("d3a3fb3f-0000-1111-88b5-bbea21c5f8ac"), BigDecimal.valueOf(3.3001d),
                                UUID.fromString("d3a3fb3f-0000-1112-88b5-bbea21c5f8ac"), BigDecimal.valueOf(3.3002d),
                                UUID.fromString("d3a3fb3f-0000-1113-88b5-bbea21c5f8ac"), BigDecimal.valueOf(3.3003d),
                                UUID.fromString("d3a3fb3f-0000-1114-88b5-bbea21c5f8ac"), BigDecimal.valueOf(3.3004d),
                                UUID.fromString("d3a3fb3f-0000-1115-88b5-bbea21c5f8ac"), BigDecimal.valueOf(3.3005d)
                        )
                )
        );
    }

    public static String getOrderServiceJson() {
        return """
                {
                    "id":"3b049946-ed7e-40ba-a7cb-f3585409da22",
                    "firstName": "Reuben",
                    "lastName": "Martin",
                    "dateBirth":"2003-11-03",
                    "orders":[
                        {
                            "id":"956bb29b-8191-4de5-9e8e-8df759525831",
                            "products": [
                                 {
                                     "id":"50faf7eb-6792-45a7-a3cd-91bb63de48f6",
                                     "name":"Phone 1",
                                     "price":100.0
                                 },
                                 {
                                     "id":"6b3a9d70-43e0-4c87-b72d-45fe79ee41c4",
                                     "name":"Car 2",
                                     "price": 222.99
                                 }
                            ],
                            "createDate":"2023-10-24T17:50:30.5470749+03:00"
                        }
                    ]
                }        
                """;
    }

    public static Customer getOrderServiceCustomerForTestJson() {
        return new Customer(
                UUID.fromString("3b049946-ed7e-40ba-a7cb-f3585409da22"),
                "Reuben",
                "Martin",
                LocalDate.of(2003, 11, 03),
                getTestOrderListForTestJson()
        );
    }

    private static List<Order> getTestOrderListForTestJson() {
        return List.of(
                new Order(
                        UUID.fromString("956bb29b-8191-4de5-9e8e-8df759525831"),
                        getTestProductListForTestJson(),
                        OffsetDateTime.parse("2023-10-24T17:50:30.5470749+03:00")
                )
        );
    }

    private static List<Product> getTestProductListForTestJson() {
        return List.of(
                new Product(
                        UUID.fromString("50faf7eb-6792-45a7-a3cd-91bb63de48f6"),
                        "Phone 1",
                        100.0,
                        null
                ),
                new Product(
                        UUID.fromString("6b3a9d70-43e0-4c87-b72d-45fe79ee41c4"),
                        "Car 2",
                        222.99,
                        null
                )
        );
    }
}
