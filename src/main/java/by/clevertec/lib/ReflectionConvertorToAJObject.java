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
import java.util.List;
import java.util.Map;
import java.util.Set;

import static by.clevertec.lib.ReflectionConvertor.INTERNAL_TYPES;
import static by.clevertec.lib.ReflectionConvertor.JAVA_INTEGER_TYPES;
import static by.clevertec.lib.ReflectionConvertor.JAVA_DOUBLE_TYPES;
import static by.clevertec.lib.ReflectionConvertor.JAVA_NON_NUMBER_STANDARD_TYPES;

public class ReflectionConvertorToAJObject {
    public AJObject toAJObject(Object object, AJConvertorContext context) {
        try {
            var serializeClass = object.getClass();
            if (INTERNAL_TYPES.contains(serializeClass.getName())) {
                return internal(object);
            } else {
                return external(object, context);
            }
        } catch (Exception e) {
            throw new AndreiJsonovichInternalException(e);
        }
    }

    private AJObject internal(Object object) {
        var value = object.toString();
        AJObject rez = new AJObject();
        var clazz = object.getClass().getName();
        if (JAVA_INTEGER_TYPES.contains(clazz)) {
            addProperty(rez, "", new AJPrimitive(AJPrimitive.ContentPresentation.LONG, value));
        } else if (JAVA_DOUBLE_TYPES.contains(clazz)) {
            addProperty(rez, "", new AJPrimitive(AJPrimitive.ContentPresentation.DOUBLE, value));
        } else if (JAVA_NON_NUMBER_STANDARD_TYPES.contains(clazz)) {
            final String type = object.getClass().getName();
            addProperty(rez, "", new AJPrimitive(getContentPresentation(type), value));
        } else {
            throw new UnsupportedOperationException(object.getClass().getName() + " dont know how convert. Please use custom convertor.");
        }
        return rez;
    }

    private AJObject external(Object object, AJConvertorContext context) throws IllegalAccessException {
        AJObject rez = new AJObject();
        var fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            var modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) continue;

            field.setAccessible(true);
            var fieldType = field.getType();

            if (JAVA_INTEGER_TYPES.contains(fieldType.toString())) {
                asIntegerNumber(rez, field, object);
            } else if (JAVA_DOUBLE_TYPES.contains(fieldType.toString())) {
                asDoubleNumber(rez, field, object);
            } else if (JAVA_NON_NUMBER_STANDARD_TYPES.contains(fieldType.toString())) {
                asNonNumberStandardType(rez, field, object);
            } else if (CharSequence.class.isAssignableFrom(fieldType)) {
                asCharSeq(rez, field, object);
            } else if (List.class.isAssignableFrom(fieldType) || Set.class.isAssignableFrom(fieldType) || fieldType.isArray()) {
                asArray(rez, field, object, context);
            } else if (Map.class.isAssignableFrom(fieldType)) {
                asMapArray(rez, field, object, context);
            } else if (Object.class.isAssignableFrom(fieldType)) {
                asObject(rez, field, object, context);
            } else {
                throw new UnsupportedOperationException(fieldType.toString() + " dont know how convert. Please use custom convertor.");
            }
        }
        return rez;
    }

    private void addProperty(AJObject target, String propertyName, AJElement value) {
        target.addProperty(
                new AJProperty(propertyName, value)
        );
    }

    private void addNumberProperty(AJObject target, AJPrimitive.ContentPresentation type, Field field, Object
            object) throws IllegalAccessException {
        addProperty(target, field.getName(),
                new AJPrimitive(type, field.get(object).toString()));
    }

    private <T> void asIntegerNumber(AJObject target, Field field, T object) throws IllegalAccessException {
        addNumberProperty(target, AJPrimitive.ContentPresentation.LONG,
                field, object);
    }

    private <T> void asDoubleNumber(AJObject target, Field field, T object) throws IllegalAccessException {
        addNumberProperty(target, AJPrimitive.ContentPresentation.DOUBLE,
                field, object);
    }

    private <T> void asNonNumberStandardType(AJObject target, Field field, T object) throws IllegalAccessException {
        final String type = field.getType().toString();
        addProperty(target, field.getName(),
                new AJPrimitive(getContentPresentation(type), field.get(object).toString())
        );
    }

    private AJPrimitive.ContentPresentation getContentPresentation(String type) {
        return switch (type) {
            case "boolean", "java.lang.Boolean" -> AJPrimitive.ContentPresentation.BOOLEAN;
            case "java.lang.String" -> AJPrimitive.ContentPresentation.STRING;
            default -> throw new UnsupportedOperationException("Convert " + type + " not supported");
        };
    }

    private <T> void asCharSeq(AJObject target, Field field, T object) throws IllegalAccessException {
        target.addProperty(
                field.getName(),
                field.get(object).toString()
        );
    }

    private <T> void asArray(AJObject target, Field field, T object, AJConvertorContext context) throws
            IllegalAccessException {
        Object[] items;
        if (Collection.class.isAssignableFrom(field.getType())) {
            items = ((Collection) field.get(object)).toArray();
        } else if (field.getType().isArray()) {
            items = (Object[]) field.get(object);
        } else {
            throw new RuntimeException("TODO"); //TODO
        }

        AJArray ajArray = new AJArray();
        for (Object item : items) {
            ajArray.add(context.toAJObject(item));
        }

        addProperty(target, field.getName(), ajArray);
    }

    private <T> void asMapArray(AJObject target, Field field, T object, AJConvertorContext context) throws
            IllegalAccessException {
        Map<Object, Object> map = (Map<Object, Object>) field.get(object);
        AJArray ajArray = new AJArray();
        for (var item : map.entrySet()) {
            var key = toAJObject(item.getKey(), context);
            var value = toAJObject(item.getValue(), context);
            AJArray itemArray = new AJArray();
            itemArray.add(key);
            itemArray.add(value);
            ajArray.add(itemArray);
        }

        addProperty(target, field.getName(), ajArray);
    }

    private <T> void asObject(AJObject target, Field field, T object, AJConvertorContext context) throws
            IllegalAccessException {
        addProperty(target, field.getName(), context.toAJObject(field.get(object)));
    }
}
