package by.clevertec.lib.printer;

import by.clevertec.lib.exceptions.AndreiJsonovichInternalException;
import by.clevertec.lib.intermediate_representation.AJArray;
import by.clevertec.lib.intermediate_representation.AJElement;
import by.clevertec.lib.intermediate_representation.AJObject;
import by.clevertec.lib.intermediate_representation.AJPrimitive;
import by.clevertec.lib.intermediate_representation.AJProperty;

public class AJPrinter {
    private StringBuilder sb = new StringBuilder();

    public String getString() {
        return sb.toString();
    }

    public String print(AJObject rootObject) {
        printElement(rootObject);
        return sb.toString();
    }

    public void printElement(AJElement element) {
        switch (element.getType()) {
            case PRIMITIVE -> printPrimitive((AJPrimitive) element);
            case PROPERTY -> printProperty((AJProperty) element);
            case OBJECT -> printObject((AJObject) element);
            case ARRAY -> printArray((AJArray) element);
            case null -> throw new NullPointerException("AJElement type is null");
            default -> throw new AndreiJsonovichInternalException("Unknown AJElement type : " + element.getType());
        }
    }

    private void printPrimitive(AJPrimitive primitive) {
        var content = primitive.getContent();
        switch (primitive.getPresentation()) {
            case STRING -> addString(content.toString());
            case BOOLEAN,
                 LONG,
                 DOUBLE -> add(content.toString());
            case NULL -> add("null");
            case null -> throw new NullPointerException("JsonPrimitive content is null");
            default ->
                    throw new AndreiJsonovichInternalException("Unknown JsonPrimitive presentation : " + primitive.getPresentation());
        }
    }

    private void printProperty(AJProperty property) {
        addString(property.getName());
        add(":");
        printElement(property.getValue());
    }

    private void printObject(AJObject object) {
        add("{");
        var propertiesIterator = object.getAll().values().iterator();
        while (propertiesIterator.hasNext()) {
            var property = propertiesIterator.next();
            printElement(property);
            if (propertiesIterator.hasNext()) add(",");
        }
        add("}");
    }

    private void printArray(AJArray array) {
        var arrayElements = array.getAll();
        if (arrayElements.isEmpty()) {
            add("[]");
            return;
        }

        add("[");
        var last = arrayElements.getLast();
        for (AJElement element : arrayElements) {
            printElement(element);
            if (element != last)
                add(",");
        }
        add("]");
    }

    private void addString(String str) {
        sb.append("\"");
        sb.append(str);
        sb.append("\"");
    }

    private void add(String str) {
        sb.append(str);
    }
}
