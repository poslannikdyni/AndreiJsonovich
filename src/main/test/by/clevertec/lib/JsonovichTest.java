package by.clevertec.lib;

import by.clevertec.project.domain.order_service.Customer;
import by.clevertec.project.domain.simple.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TestData;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonovichTest {

    private Jsonovich jsonovich;

    @BeforeEach
    void init(){
        jsonovich = new Jsonovich();
    }

//    @Test
//    void shouldSuccessfullyTransferFromCustomerToJsonToCustomer() {
//        // given
//        jsonovich.register(new CustomerConvertor());
//        jsonovich.register(new OrderConvertor());
//        jsonovich.register(new ProductConvertor());
//
//        var expectedCustomer = TestData.getOrderServiceTestCustomer();
//        var json = jsonovich.buildJson(expectedCustomer);
//        Customer actualCustomer = jsonovich.buildObject(Customer.class, json);
//
//        // when
//
//        // then
//        assertEquals(expectedCustomer, actualCustomer);
//    }
//
//    @Test
//    void shouldBuildCustomerFromJson() {
//        // given
//        jsonovich.register(new CustomerConvertor());
//        jsonovich.register(new OrderConvertor());
//        jsonovich.register(new ProductConvertor());
//
//        Customer actualCustomer = jsonovich.buildObject(Customer.class, TestData.getOrderServiceJson());
//
//        // when
//
//        // then
//        assertEquals(TestData.getOrderServiceCustomerForTestJson(), actualCustomer);
//    }

    @Test
    void shouldRunReflectionSerialization() {
        var expected = TestData.getSimpleServiceTestData();
        var json = jsonovich.buildJson(expected);
        A actual = jsonovich.buildObject(A.class, json);
        assertEquals(expected, actual);
    }
}