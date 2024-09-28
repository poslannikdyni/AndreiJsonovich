package by.clevertec.lib.api;

import by.clevertec.lib.intermediate_representation.AJElement;
import by.clevertec.lib.intermediate_representation.AJObject;

public interface AJConvertorContext {
    <T> AJElement toAJObject(T object);
    <T> T toUserType(Class<T> clazz, AJObject object);
}
