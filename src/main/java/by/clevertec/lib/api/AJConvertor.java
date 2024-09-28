package by.clevertec.lib.api;

import by.clevertec.lib.intermediate_representation.AJObject;

public interface AJConvertor<T> {
    Class<T> forType();
    AJObject toAJObject(T object, AJConvertorContext context);
    T toUserType(AJObject element, AJConvertorContext context);
}
