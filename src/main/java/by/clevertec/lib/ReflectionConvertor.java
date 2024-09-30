package by.clevertec.lib;

import by.clevertec.lib.api.AJConvertorContext;
import by.clevertec.lib.exceptions.AndreiJsonovichInternalException;
import by.clevertec.lib.intermediate_representation.AJArray;
import by.clevertec.lib.intermediate_representation.AJElement;
import by.clevertec.lib.intermediate_representation.AJObject;
import by.clevertec.lib.intermediate_representation.AJPrimitive;
import by.clevertec.lib.intermediate_representation.AJProperty;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReflectionConvertor {
    static final Set<String> INTERNAL_TYPES = Set.of(
            "java.lang.Byte",
            "java.lang.Short",
            "java.lang.Integer",
            "java.lang.Long",
            "java.lang.Float",
            "java.lang.Double",
            "java.lang.Boolean",
            "java.lang.Character",
            "java.lang.String"
    );

    static final Set<String> JAVA_INTEGER_TYPES = Set.of(
            "byte",
            "short",
            "int",
            "long",
            "java.lang.Byte",
            "java.lang.Short",
            "java.lang.Integer",
            "java.lang.Long"
    );

    static final Set<String> JAVA_DOUBLE_TYPES = Set.of(
            "float",
            "double",
            "java.lang.Float",
            "java.lang.Double"
    );

    static final Set<String> JAVA_NON_NUMBER_STANDARD_TYPES = Set.of(
            "boolean",
            "char",
            "java.lang.Boolean",
            "java.lang.Character"
    );

    static final Set<String> JAVA_TYPES;
    static {
        var tmp = new HashSet<String>();
        tmp.addAll(JAVA_INTEGER_TYPES);
        tmp.addAll(JAVA_DOUBLE_TYPES);
        tmp.addAll(JAVA_NON_NUMBER_STANDARD_TYPES);
        tmp.add("java.lang.String");
        JAVA_TYPES = Collections.unmodifiableSet(tmp);
    }

    private ReflectionConvertorToAJObject toAJObjectConverter = new ReflectionConvertorToAJObject();
    private ReflectionConvertorToUserType toUserTypeConverter = new ReflectionConvertorToUserType();

    public AJObject toAJObject(Object object, AJConvertorContext context) {
        return toAJObjectConverter.toAJObject(object, context);
    }


    public <T> T toUserType(Class<T> requestType, AJObject object, AJConvertorContext context) {
        return toUserTypeConverter.toUserType(requestType, object, context);
    }

}