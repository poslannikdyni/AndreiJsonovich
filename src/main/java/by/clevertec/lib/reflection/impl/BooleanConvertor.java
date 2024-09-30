package by.clevertec.lib.reflection.impl;

import by.clevertec.lib.api.AJConvertorContext;
import by.clevertec.lib.intermediate_representation.AJElement;
import by.clevertec.lib.intermediate_representation.AJObject;
import by.clevertec.lib.intermediate_representation.AJPrimitive;
import by.clevertec.lib.reflection.TypeConvertor;

import java.util.Set;

public class BooleanConvertor implements TypeConvertor<Boolean> {
    @Override
    public Set<String> getAlias() {
        return Set.of(
                "boolean",
                "java.lang.Boolean"
        );
    }

    @Override
    public AJElement.Type expectedType() {
        return AJElement.Type.PRIMITIVE;
    }

    @Override
    public AJPrimitive.ContentPresentation expectedPrimitiveContentPresentation() {
        return AJPrimitive.ContentPresentation.BOOLEAN;
    }

    @Override
    public AJElement toAJElement(Boolean field) {
        return new AJPrimitive(AJPrimitive.ContentPresentation.BOOLEAN, field.toString());
    }

    @Override
    public Boolean toUserType(AJElement element) {
        var field = (AJPrimitive) element;
        return Boolean.parseBoolean(field.getContent().toString());
    }
}
