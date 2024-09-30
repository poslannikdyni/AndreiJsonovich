package by.clevertec.lib.reflection;

import by.clevertec.lib.api.AJConvertorContext;
import by.clevertec.lib.intermediate_representation.AJElement;
import by.clevertec.lib.intermediate_representation.AJObject;
import by.clevertec.lib.intermediate_representation.AJPrimitive;

import java.lang.reflect.Field;
import java.util.Set;

public interface TypeConvertor<T> {
    Set<String> getAlias();
    AJElement.Type expectedType();
    AJPrimitive.ContentPresentation expectedPrimitiveContentPresentation();
    AJElement toAJElement(T field);
    T toUserType(AJElement element);

    default void expected(AJElement.Type type, AJElement element) {
        if (element.getType() != type)
            throw new ClassCastException("Expected " + type + ", got " + element.getType() + System.lineSeparator() + element.toString());
    }

    default void expected(AJPrimitive.ContentPresentation contentPresentation, AJPrimitive field) {
        if (field.getPresentation() != contentPresentation)
            throw new ClassCastException("Expected " + contentPresentation + ", got " + field.getPresentation() + System.lineSeparator() + field.toString());
    }
}
