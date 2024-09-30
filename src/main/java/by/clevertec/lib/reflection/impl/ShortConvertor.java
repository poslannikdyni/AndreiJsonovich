package by.clevertec.lib.reflection.impl;

import by.clevertec.lib.api.AJConvertorContext;
import by.clevertec.lib.intermediate_representation.AJElement;
import by.clevertec.lib.intermediate_representation.AJPrimitive;
import by.clevertec.lib.reflection.TypeConvertor;

import java.util.Set;

public class ShortConvertor implements TypeConvertor<Short> {
    @Override
    public Set<String> getAlias() {
        return Set.of(
                "short",
                "java.lang.Short"
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
    public AJElement toAJElement(Short field, AJConvertorContext context){
        return new AJPrimitive(AJPrimitive.ContentPresentation.LONG, field.toString());
    }

    @Override
    public Short toUserType(AJElement element, AJConvertorContext context){
        var field = (AJPrimitive) element;
        return Short.parseShort(field.getContent().toString());
    }
}