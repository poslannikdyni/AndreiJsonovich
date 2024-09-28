package by.clevertec;

import by.clevertec.lib.Jsonovich;
import by.clevertec.project.convertor.order_service.CustomerConvertor;
import by.clevertec.project.convertor.order_service.OrderConvertor;
import by.clevertec.project.convertor.order_service.ProductConvertor;
import by.clevertec.project.domain.order_service.Customer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;

import static by.clevertec.Data.buildTestData;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println(Locale.getDefault());
        System.out.println(Charset.defaultCharset());

        var jsonovich = new Jsonovich();
        jsonovich.register(new CustomerConvertor());
        jsonovich.register(new OrderConvertor());
        jsonovich.register(new ProductConvertor());

        var testData = buildTestData();
        var json = jsonovich.buildJson(testData);
        System.out.println(json);

        Customer p = jsonovich.buildObject(Customer.class, json);
        System.out.println(p);

        Customer p1 = jsonovich.buildObject(Customer.class, Data.getJson());
        System.out.println(p1);

        System.out.println("testData == p " + (testData == p));
        System.out.println("testData.equals(p) " + (testData.equals(p)));
    }
}