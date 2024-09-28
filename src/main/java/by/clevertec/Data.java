package by.clevertec;

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

public class Data {
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

    public static Customer buildTestData() {
        return new Customer(
                UUID.randomUUID(),
                "CustomerName",
                "CustomerLastName",
                LocalDate.now(),
                buildOrdersList()
        );
    }

    private static List<Order> buildOrdersList() {
        return List.of(
                new Order(UUID.randomUUID(),
                        buildProductList1(),
                        OffsetDateTime.now()
                ),
                new Order(UUID.randomUUID(),
                        buildProductList2(),
                        OffsetDateTime.now()
                ),
                new Order(UUID.randomUUID(),
                        buildProductList3(),
                        OffsetDateTime.now()
                )
        );
    }

    private static List<Product> buildProductList1() {
        return List.of(
                new Product(UUID.randomUUID(),
                        "Product1",
                        1.0d,
                        new HashMap<>()
                )
        );
    }

    private static List<Product> buildProductList2() {
        return List.of(
                new Product(UUID.randomUUID(),
                        "Product2.1",
                        2.1d,
                        new HashMap<>()
                ),
                new Product(UUID.randomUUID(),
                        "Product2.2",
                        2.2d,
                        new HashMap<>()
                )
        );
    }

    private static List<Product> buildProductList3() {
        return List.of(
                new Product(UUID.randomUUID(),
                        "Product3.1",
                        3.1d,
                        Map.of(
                                UUID.randomUUID(), BigDecimal.valueOf(3.1001d)
                        )
                ),
                new Product(UUID.randomUUID(),
                        "Product3.2",
                        3.2d,
                        Map.of(
                                UUID.randomUUID(), BigDecimal.valueOf(3.2001d),
                                UUID.randomUUID(), BigDecimal.valueOf(3.2002d)
                        )
                ),
                new Product(UUID.randomUUID(),
                        "Product3.3",
                        3.3d,
                        Map.of(
                                UUID.randomUUID(), BigDecimal.valueOf(3.3001d),
                                UUID.randomUUID(), BigDecimal.valueOf(3.3002d),
                                UUID.randomUUID(), BigDecimal.valueOf(3.3003d),
                                UUID.randomUUID(), BigDecimal.valueOf(3.3004d),
                                UUID.randomUUID(), BigDecimal.valueOf(3.3005d)
                        )
                )
        );
    }

    public static String getJson(){
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
}
