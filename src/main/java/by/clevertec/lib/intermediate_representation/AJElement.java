package by.clevertec.lib.intermediate_representation;

public interface AJElement {
    enum Type {
        PRIMITIVE,
        PROPERTY,
        OBJECT,
        ARRAY;
    }

    Type getType();
}
