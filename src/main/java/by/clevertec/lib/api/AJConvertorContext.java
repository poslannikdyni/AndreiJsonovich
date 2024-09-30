package by.clevertec.lib.api;

import by.clevertec.lib.intermediate_representation.AJElement;
import by.clevertec.lib.intermediate_representation.AJObject;

public interface AJConvertorContext {
    <T> AJElement toAJElement(T object);
    <T> T toUserType(Class<T> clazz, AJElement object);
}
