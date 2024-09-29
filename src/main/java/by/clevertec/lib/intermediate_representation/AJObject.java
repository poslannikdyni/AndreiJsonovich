package by.clevertec.lib.intermediate_representation;

import by.clevertec.lib.exceptions.AndreiJsonovichInternalException;
import by.clevertec.lib.printer.AJPrinter;

import java.util.LinkedHashMap;
import java.util.Map;

public class AJObject implements AJElement {
    private Map<String, AJProperty> properties = new LinkedHashMap<>();

    @Override
    public Type getType() {
        return Type.OBJECT;
    }

    public void addProperty(String name, String value) {
        properties.put(name, AJProperty.builder()
                .setName(name)
                .setValue(new AJPrimitive(AJPrimitive.ContentPresentation.STRING, value))
                .build());
    }

    public void addProperty(String name, AJElement element) {
        properties.put(name, AJProperty.builder()
                .setName(name)
                .setValue(element)
                .build());
    }

    public void addProperty(AJProperty property) {
        properties.put(property.getName(), property);
    }

    public AJArray getAsArray(String propertyName) {
        var property = properties.get(propertyName);
        if (property == null) return null;
        if (property.getValue().getType() != Type.ARRAY) throw new AndreiJsonovichInternalException(propertyName + " must be an array");

        return (AJArray) property.getValue();
    }

    public String getAsString(String propertyName) {
        var property = properties.get(propertyName);
        if (property == null) return null;

        if (property.getValue().getType() == Type.PRIMITIVE)
            return ((AJPrimitive) property.getValue()).getContent().toString();

        AJPrinter printer = new AJPrinter();
        printer.printElement(property.getValue());
        return printer.getString();
    }

    public Map<String, AJProperty> getAll() {
        return properties;
    }

    public AJElement getNull() {
        return new AJPrimitive(AJPrimitive.ContentPresentation.NULL, null);
    }
}
