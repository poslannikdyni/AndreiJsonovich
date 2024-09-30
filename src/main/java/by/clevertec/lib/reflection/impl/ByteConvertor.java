package by.clevertec.lib.reflection.impl;

import by.clevertec.lib.intermediate_representation.AJElement;
import by.clevertec.lib.intermediate_representation.AJPrimitive;
import by.clevertec.lib.reflection.TypeConvertor;

import java.util.Set;

public class ByteConvertor implements TypeConvertor<Byte> {
    @Override
    public Set<String> getAlias() {
        return Set.of(
                "byte",
                "java.lang.Byte"
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
    public AJElement toAJElement(Byte field) {
        return new AJPrimitive(AJPrimitive.ContentPresentation.LONG, field.toString());
    }

    @Override
    public Byte toUserType(AJElement element) {
        var field = (AJPrimitive) element;
        return Byte.parseByte(field.getContent().toString());
    }
}
