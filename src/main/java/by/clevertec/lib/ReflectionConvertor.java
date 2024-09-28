package by.clevertec.lib;

import by.clevertec.lib.api.AJConvertorContext;
import by.clevertec.lib.intermediate_representation.AJArray;
import by.clevertec.lib.intermediate_representation.AJElement;
import by.clevertec.lib.intermediate_representation.AJObject;
import by.clevertec.lib.intermediate_representation.AJPrimitive;
import by.clevertec.lib.intermediate_representation.AJProperty;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReflectionConvertor{
    private static final Set<String> JAVA_INTEGER_TYPES = Set.of(
            "byte",
            "short",
            "int",
            "long",
            "java.lang.Byte",
            "java.lang.Short",
            "java.lang.Int",
            "java.lang.Long"
    );

    private static final Set<String> JAVA_DOUBLE_TYPES = Set.of(
            "float",
            "double",
            "java.lang.Float",
            "java.lang.Double"
    );

    private static final Set<String> JAVA_NON_NUMBER_STANDARD_TYPES = Set.of(
            "boolean",
            "char",
            "void",
            "java.lang.Boolean",
            "java.lang.Character",
            "java.lang.Void"
    );

    public AJObject toAJObject(Object object, AJConvertorContext context) {
       throw new RuntimeException("TODO"); //TODO
    }

    public <T> T toUserType(Class<T> requestType, AJObject element, AJConvertorContext context) {
        throw new RuntimeException("TODO"); // TODO
    }
}