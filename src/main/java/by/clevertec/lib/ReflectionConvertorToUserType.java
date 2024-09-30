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
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static by.clevertec.lib.ReflectionConvertor.INTERNAL_TYPES;
import static by.clevertec.lib.ReflectionConvertor.JAVA_TYPES;

public class ReflectionConvertorToUserType {
    public <T> T toUserType(Class<T> requestType, AJObject object, AJConvertorContext context) {
        try {
            if (INTERNAL_TYPES.contains(requestType.getName())) {
                return internal(requestType, object);
            } else {
                return external(requestType, object, context);
            }
        } catch (Exception e) {
            throw new AndreiJsonovichInternalException(e);
        }
    }

    private <T> T internal(Class<T> requestType, AJObject object) {
        var clazz = requestType.getName();
        var valueProperty = object.getProperty("");
        if (valueProperty == null)
            throw new AndreiJsonovichInternalException("Not found empty property in json object.");

        var contentType = valueProperty.getValue().getType();
        //        String value = ((AJPrimitive) property.getValue()).getContent().toString();
        //        switch (field.getType().getName()) {
        //            case "byte", "java.lang.Byte" ->  field.setByte(instance, Byte.parseByte(value));
        //            case "short", "java.lang.Short" ->  field.setShort(instance, Short.parseShort(value));
        //            case "int", "java.lang.Integer" ->  field.setInt(instance, Integer.parseInt(value));
        //            case "long", "java.lang.Long" ->  field.setLong(instance, Long.parseLong(value));
        //            case "float", "java.lang.Float" ->  field.setFloat(instance, Float.parseFloat(value));
        //            case "double", "java.lang.Double" ->  field.setDouble(instance, Double.parseDouble(value));
        //            case "boolean", "java.lang.Boolean" ->  field.setBoolean(instance, Boolean.parseBoolean(value));
        //            case "char", "java.lang.Character" ->  field.setChar(instance, value.charAt(0));
        //            case "java.lang.String" ->  field.set(instance, value);
        //            default -> throw new UnsupportedOperationException(field.getType().getName() + " dont know how convert. Please use custom convertor.");
        //        }


//        return switch (clazz) {
//            case "java.lang.Byte" -> new Byte(1);
//            case "java.lang.Short" -> new Integer(1);
//            case "java.lang.Integer" -> new Integer(1);
//            case "java.lang.Long" -> new Integer(1);
//            case "java.lang.Float" -> new Integer(1);
//            case "java.lang.Double" -> new Integer(1);
//            case "java.lang.Boolean" -> new Integer(1);
//            case "java.lang.Character" -> new Integer(1);
//            case "java.lang.String" -> new Integer(1);
//            default ->
//                    throw new UnsupportedOperationException("Dont convert json class " + clazz + " to object. Please use custom convertor.");
//        }
//
//
//        if (JAVA_INTEGER_TYPES.contains(clazz)) {
//            addProperty(rez, "", new AJPrimitive(AJPrimitive.ContentPresentation.LONG, value));
//        } else if (JAVA_DOUBLE_TYPES.contains(clazz)) {
//            addProperty(rez, "", new AJPrimitive(AJPrimitive.ContentPresentation.DOUBLE, value));
//        } else if (JAVA_NON_NUMBER_STANDARD_TYPES.contains(clazz)) {
//            final String type = object.getClass().getName();
//            addProperty(rez, "", new AJPrimitive(getContentPresentation(type), value));
//        } else {
//            throw new UnsupportedOperationException(object.getClass().getName() + " dont know how convert. Please use custom convertor.");
//        }
        return null;
    }

    private <T> T external(Class<T> requestType, AJObject object, AJConvertorContext context) throws IllegalAccessException {
        try {
            T instance = requestType.newInstance();
            var fields = requestType.getDeclaredFields();
            for (Field field : fields) {
                var modifiers = field.getModifiers();
                if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) continue;

                field.setAccessible(true);
                var fieldType = field.getType();
                var property = object.getProperty(field.getName());

                if (JAVA_TYPES.contains(fieldType.getName())) {
                    asStandartType(instance, field, property);
                } else if (List.class.isAssignableFrom(fieldType) || Set.class.isAssignableFrom(fieldType) || fieldType.isArray()) {
                    asListOrSetOrArray(instance, field, property, context);
                } else if (Map.class.isAssignableFrom(fieldType)) {
                    asMap(instance, field, property, context);
                } else if (Object.class.isAssignableFrom(fieldType)) {
                    asObject(instance, field, object, context);
                } else {
                    throw new UnsupportedOperationException(fieldType.toString() + " dont know how convert. Please use custom convertor.");
                }
            }
            return instance;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> void asStandartType(T instance, Field field, AJProperty property) throws IllegalAccessException {
        if (property.getValue().getType() != AJElement.Type.PRIMITIVE)
            throw new AndreiJsonovichInternalException("Unexpected behaviour");

        String value = ((AJPrimitive) property.getValue()).getContent().toString();
        switch (field.getType().getName()) {
            case "byte", "java.lang.Byte" -> field.setByte(instance, Byte.parseByte(value));
            case "short", "java.lang.Short" -> field.setShort(instance, Short.parseShort(value));
            case "int", "java.lang.Integer" -> field.setInt(instance, Integer.parseInt(value));
            case "long", "java.lang.Long" -> field.setLong(instance, Long.parseLong(value));
            case "float", "java.lang.Float" -> field.setFloat(instance, Float.parseFloat(value));
            case "double", "java.lang.Double" -> field.setDouble(instance, Double.parseDouble(value));
            case "boolean", "java.lang.Boolean" -> field.setBoolean(instance, Boolean.parseBoolean(value));
            case "char", "java.lang.Character" -> field.setChar(instance, value.charAt(0));
            case "java.lang.String" -> field.set(instance, value);
            default ->
                    throw new UnsupportedOperationException(field.getType().getName() + " dont know how convert. Please use custom convertor.");
        }
    }

    private <T> void asCharSeq(T instance, Field field, AJProperty property) throws IllegalAccessException {
        if (property.getValue().getType() != AJElement.Type.PRIMITIVE)
            throw new AndreiJsonovichInternalException("Unexpected behaviour");

        String value = ((AJPrimitive) property.getValue()).getContent().toString();
        field.set(instance, value);
    }

    private <T> void asListOrSetOrArray(T instance, Field field, AJProperty property, AJConvertorContext context) throws ClassNotFoundException, IllegalAccessException {
        if (property.getValue().getType() != AJElement.Type.ARRAY)
            throw new AndreiJsonovichInternalException("Unexpected behaviour");

        var listWithGeneric = field.getGenericType();
        Class actualType = null;
        if (listWithGeneric instanceof ParameterizedType p) {
            if (p.getActualTypeArguments().length < 1)
                throw new AndreiJsonovichInternalException("Expected parameterized type");
            actualType = Class.forName(p.getActualTypeArguments()[0].getTypeName());
        }

        AJArray array = (AJArray) property.getValue();
        List<Object> list = new ArrayList<>();
        for (AJElement ajElement : array.getAll()) {
            list.add(context.toUserType(actualType, (AJObject) ajElement));
        }

        if (List.class.isAssignableFrom(field.getType()))
            field.set(instance, list);
        else if (Set.class.isAssignableFrom(field.getType()))
            field.set(instance, new HashSet<>(list));
        else if (field.getType().isArray())
            field.set(instance, list.toArray());
        else throw new AndreiJsonovichInternalException("Unexpected behaviour");

    }

    private <T> void asMap(T instance, Field field, AJProperty property, AJConvertorContext context) {
//
    }

    private <T> void asObject(AJObject target, Field field, T object, AJConvertorContext context) throws
            IllegalAccessException {
        addProperty(target, field.getName(), context.toAJObject(field.get(object)));
    }
}
