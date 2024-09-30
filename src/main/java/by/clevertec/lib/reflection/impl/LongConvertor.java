package by.clevertec.lib.reflection.impl;

import by.clevertec.lib.api.AJConvertorContext;
import by.clevertec.lib.intermediate_representation.AJElement;
import by.clevertec.lib.intermediate_representation.AJPrimitive;
import by.clevertec.lib.reflection.TypeConvertor;

import java.util.Set;

public class LongConvertor implements TypeConvertor<Long> {
    @Override
    public Set<String> getAlias() {
        return Set.of(
                "long",
                "java.lang.Long"
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
    public AJElement toAJElement(Long field, AJConvertorContext context){
        return new AJPrimitive(AJPrimitive.ContentPresentation.LONG, field.toString());
    }

    @Override
    public Long toUserType(AJElement element, AJConvertorContext context){
        var field = (AJPrimitive) element;
        return Long.parseLong(field.getContent().toString());
    }
}
