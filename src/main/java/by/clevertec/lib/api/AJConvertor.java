package by.clevertec.lib.api;

import by.clevertec.lib.intermediate_representation.AJElement;
import by.clevertec.lib.intermediate_representation.AJObject;

public interface AJConvertor<T> {
    Class<T> forType();
    AJElement toAJElement(T object, AJConvertorContext context);
    T toUserType(AJElement element, AJConvertorContext context);

    default AJObject expectedObject(AJElement element) {
        if (element.getType() != AJElement.Type.OBJECT)
            throw new ClassCastException("Expected OBJECT, got " + element.getType() + System.lineSeparator() + element.toString());
        return (AJObject) element;
    }
}
