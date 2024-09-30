package by.clevertec.lib.reflection.impl;

import by.clevertec.lib.intermediate_representation.AJElement;
import by.clevertec.lib.intermediate_representation.AJPrimitive;
import by.clevertec.lib.reflection.TypeConvertor;

import java.util.Set;

public class IntegerConvertor implements TypeConvertor<Integer> {
    @Override
    public Set<String> getAlias() {
        return Set.of(
                "int",
                "java.lang.Integer"
        );
    }

    @Override
    public AJElement.Type expectedType() {
        return AJElement.Type.PRIMITIVE;
    }

    @Override
    public AJPrimitive.ContentPresentation expectedPrimitiveContentPresentation() {
        return AJPrimitive.ContentPresentation.LONG;
    }

    @Override
    public AJElement toAJElement(Integer field) {
        return new AJPrimitive(AJPrimitive.ContentPresentation.LONG, field.toString());
    }

    @Override
    public Integer toUserType(AJElement element) {
        var field = (AJPrimitive) element;
        return Integer.parseInt(field.getContent().toString());
    }
}
