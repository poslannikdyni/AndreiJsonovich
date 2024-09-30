package by.clevertec.lib;

import by.clevertec.lib.api.AJConvertorContext;
import by.clevertec.lib.exceptions.AndreiJsonovichInternalException;
import by.clevertec.lib.intermediate_representation.AJElement;
import by.clevertec.lib.intermediate_representation.AJObject;
import by.clevertec.lib.intermediate_representation.AJProperty;
import by.clevertec.lib.reflection.TypeConvertor;
import by.clevertec.lib.reflection.impl.BooleanConvertor;
import by.clevertec.lib.reflection.impl.ByteConvertor;
import by.clevertec.lib.reflection.impl.DoubleConvertor;
import by.clevertec.lib.reflection.impl.FloatConvertor;
import by.clevertec.lib.reflection.impl.IntegerConvertor;
import by.clevertec.lib.reflection.impl.LongConvertor;
import by.clevertec.lib.reflection.impl.ShortConvertor;
import by.clevertec.lib.reflection.impl.StringConverter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ReflectionConvertor {
    static final Map<String, TypeConvertor> CONVERTORS;

    private static void addType(Map<String, TypeConvertor> map, TypeConvertor convertor) {
        for (var alias : convertor.getAlias()) {
            map.put(alias.toString(), convertor);
        }
    }

    static {
        Map<String, TypeConvertor> map = new HashMap<>();
        addType(map, new BooleanConvertor());
        addType(map, new ByteConvertor());
        addType(map, new DoubleConvertor());
        addType(map, new FloatConvertor());
        addType(map, new IntegerConvertor());
        addType(map, new LongConvertor());
        addType(map, new ShortConvertor());
        addType(map, new StringConverter());
        CONVERTORS = Collections.unmodifiableMap(map);
    }

    public <T> AJElement toAJElement(T object, JsonovichContext context) {
        try {
            var serializeClass = object.getClass().getName();
            var standartConvertor = CONVERTORS.get(serializeClass);
            if (standartConvertor != null) {
                return addProperty(new AJObject(), "", standartConvertor.toAJElement(object, context));
            } else {
                return decompose(object, context);
            }
        } catch (Exception e) {
            throw new AndreiJsonovichInternalException(e);
        }
    }

    private AJObject addProperty(AJObject target, String propertyName, AJElement value) {
        target.addProperty(new AJProperty(propertyName, value));
        return target;
    }

    private AJObject decompose(Object object, AJConvertorContext context) throws IllegalAccessException {
        AJObject rez = new AJObject();
        var fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            var modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) continue;

            field.setAccessible(true);
            var fieldType = field.getType().getName();
            var convertor = CONVERTORS.get(fieldType);

            if (convertor != null) {
                rez.addProperty(new AJProperty(field.getName(), convertor.toAJElement(field.get(object), context)));
            } else if (Object.class.isAssignableFrom(field.getType())) {
                addProperty(rez, field.getName(), context.toAJElement(field.get(object)));
            } else {
                throw new UnsupportedOperationException(fieldType.toString() + " dont know how convert. Please use custom convertor.");
            }
        }
        return rez;
    }

    public <T> T toUserType(Class<T> requestType, AJElement element, AJConvertorContext context) {
        try {
            var deserializeClass = requestType.getName();
            var standartConvertor = CONVERTORS.get(deserializeClass);
            if (standartConvertor != null) {
                return (T) standartConvertor.toUserType(element, context);
            } else {
                return build(requestType, element, context);
            }
        } catch (Exception e) {
            throw new AndreiJsonovichInternalException(e);
        }
    }

    private <T> T build(Class<T> requestType, AJElement element, AJConvertorContext context) {
        if(element.getType() != AJElement.Type.OBJECT)
            throw new AndreiJsonovichInternalException("Json convert failed");

        try {
            T instance = requestType.newInstance();
            var fields = requestType.getDeclaredFields();
            for (Field field : fields) {
                var modifiers = field.getModifiers();
                if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) continue;

                field.setAccessible(true);
                var fieldType = field.getType().getName();
                var property = ((AJObject)element).getProperty(field.getName());
                var convertor = CONVERTORS.get(fieldType);

                if (convertor != null) {
                    field.set(instance, convertor.toUserType(element, context));
                } else if (Object.class.isAssignableFrom(field.getType())) {
                    field.set(instance, context.toUserType(field.getType(), property));
                } else {
                    throw new UnsupportedOperationException(fieldType.toString() + " dont know how convert. Please use custom convertor.");
                }
            }
            return instance;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    //    private <T> void asListOrSetOrArray(T instance, Field field, AJProperty property, AJConvertorContext context) throws ClassNotFoundException, IllegalAccessException {
    //        if (property.getValue().getType() != AJElement.Type.ARRAY)
    //            throw new AndreiJsonovichInternalException("Unexpected behaviour");
    //
    //        var listWithGeneric = field.getGenericType();
    //        Class actualType = null;
    //        if (listWithGeneric instanceof ParameterizedType p) {
    //            if (p.getActualTypeArguments().length < 1)
    //                throw new AndreiJsonovichInternalException("Expected parameterized type");
    //            actualType = Class.forName(p.getActualTypeArguments()[0].getTypeName());
    //        }
    //
    //        AJArray array = (AJArray) property.getValue();
    //        List<Object> list = new ArrayList<>();
    //        for (AJElement ajElement : array.getAll()) {
    //            list.add(context.toUserType(actualType, (AJObject) ajElement));
    //        }
    //
    //        if (List.class.isAssignableFrom(field.getType()))
    //            field.set(instance, list);
    //        else if (Set.class.isAssignableFrom(field.getType()))
    //            field.set(instance, new HashSet<>(list));
    //        else if (field.getType().isArray())
    //            field.set(instance, list.toArray());
    //        else throw new AndreiJsonovichInternalException("Unexpected behaviour");
    //
    //    }
    //    private <T> void asObject(AJObject target, Field field, T object, AJConvertorContext context) throws
    //            IllegalAccessException {
    //        addProperty(target, field.getName(), context.toAJObject(field.get(object)));
    //    }
}