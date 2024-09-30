package by.clevertec.lib.reflection.impl;

import by.clevertec.lib.api.AJConvertorContext;
import by.clevertec.lib.intermediate_representation.AJElement;
import by.clevertec.lib.intermediate_representation.AJPrimitive;
import by.clevertec.lib.reflection.TypeConvertor;

import java.util.Set;

public class DoubleConvertor implements TypeConvertor<Double> {
    @Override
    public Set<String> getAlias() {
        return Set.of(
                "double",
                "java.lang.Double"
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
    public AJElement toAJElement(Double field, AJConvertorContext context){
        return new AJPrimitive(AJPrimitive.ContentPresentation.DOUBLE, field.toString());
    }

    @Override
    public Double toUserType(AJElement element, AJConvertorContext context){
        var field = (AJPrimitive) element;
        return Double.parseDouble(field.getContent().toString());
    }
}
