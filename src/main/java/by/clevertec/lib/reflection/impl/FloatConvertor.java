package by.clevertec.lib.reflection.impl;

import by.clevertec.lib.intermediate_representation.AJElement;
import by.clevertec.lib.intermediate_representation.AJPrimitive;
import by.clevertec.lib.reflection.TypeConvertor;

import java.util.Set;

public class FloatConvertor implements TypeConvertor<Float> {
    @Override
    public Set<String> getAlias() {
        return Set.of(
                "float",
                "java.lang.Float"
        );
    }

    @Override
    public AJElement.Type expectedType() {
        return AJElement.Type.PRIMITIVE;
    }

    @Override
    public AJPrimitive.ContentPresentation expectedPrimitiveContentPresentation() {
        return AJPrimitive.ContentPresentation.DOUBLE;
    }

    @Override
    public AJElement toAJElement(Float field) {
        return new AJPrimitive(AJPrimitive.ContentPresentation.DOUBLE, field.toString());
    }

    @Override
    public Float toUserType(AJElement element) {
        var field = (AJPrimitive) element;
        return Float.parseFloat(field.getContent().toString());
    }
}
