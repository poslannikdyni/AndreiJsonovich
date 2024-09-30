package by.clevertec.lib.reflection.impl;

import by.clevertec.lib.api.AJConvertorContext;
import by.clevertec.lib.intermediate_representation.AJElement;
import by.clevertec.lib.intermediate_representation.AJPrimitive;
import by.clevertec.lib.reflection.TypeConvertor;

import java.util.Set;

public class StringConverter implements TypeConvertor<String> {
    @Override
    public Set<String> getAlias() {
        return Set.of(
                "java.lang.String"
        );
    }

    @Override
    public AJElement.Type expectedType() {
        return AJElement.Type.PRIMITIVE;
    }

    @Override
    public AJPrimitive.ContentPresentation expectedPrimitiveContentPresentation() {
        return AJPrimitive.ContentPresentation.STRING;
    }

    @Override
    public AJElement toAJElement(String field, AJConvertorContext context){
        return new AJPrimitive(AJPrimitive.ContentPresentation.STRING, field);
    }

    @Override
    public String toUserType(AJElement element, AJConvertorContext context){
        var field = (AJPrimitive) element;
        return field.getContent().toString();
    }
}
