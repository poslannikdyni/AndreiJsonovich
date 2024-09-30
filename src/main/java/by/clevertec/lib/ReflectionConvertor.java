package by.clevertec.lib;

import by.clevertec.lib.api.AJConvertorContext;
import by.clevertec.lib.exceptions.AndreiJsonovichInternalException;
import by.clevertec.lib.intermediate_representation.AJArray;
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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
            var fieldType = field.getType();
            var convertor = CONVERTORS.get(fieldType.getName());

            if (List.class.isAssignableFrom(fieldType) || Set.class.isAssignableFrom(fieldType) || fieldType.isArray()) {
                asArray(rez, field, object, context);
            } else if (Map.class.isAssignableFrom(fieldType)) {
                asMapArray(rez, field, object, context);
            } else if (convertor != null) {
                rez.addProperty(new AJProperty(field.getName(), convertor.toAJElement(field.get(object), context)));
            } else if (Object.class.isAssignableFrom(field.getType())) {
                addProperty(rez, field.getName(), context.toAJElement(field.get(object)));
            } else {
                throw new UnsupportedOperationException(fieldType.toString() + " dont know how convert. Please use custom convertor.");
            }
        }
        return rez;
    }

    private void asArray(AJObject rez, Field field, Object object, AJConvertorContext context) throws IllegalAccessException {
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
            ajArray.add(context.toAJElement(item));
        }

        addProperty(rez, field.getName(), ajArray);
    }

    private <T> void asMapArray(AJObject rez, Field field, T object, AJConvertorContext context) throws IllegalAccessException {
        Map<Object, Object> map = (Map<Object, Object>) field.get(object);
        AJArray ajArray = new AJArray();
        for (var item : map.entrySet()) {
            var key = context.toAJElement(item.getKey());
            var value = context.toAJElement(item.getValue());
            AJArray itemArray = new AJArray();
            itemArray.add(key);
            itemArray.add(value);
            ajArray.add(itemArray);
        }

        addProperty(rez, field.getName(), ajArray);
    }

    public <T> T toUserType(Class<T> requestType, AJElement element, AJConvertorContext context) {
        try {
            if (List.class.isAssignableFrom(requestType)) {
                return toList(requestType, element, context);
            }else if (Set.class.isAssignableFrom(requestType)) {
                return toSet(requestType, element, context);
            }else if (requestType.isArray()) {
                return toArray(requestType, element, context);
            } else if (Map.class.isAssignableFrom(requestType)) {
                return toMap(requestType, element, context);
            } else {
                return advanced(requestType, element, context);
            }
        } catch (Exception e) {
            throw new AndreiJsonovichInternalException(e);
        }
    }

    private <T> T toList(Class<T> requestType, AJElement element, AJConvertorContext context) {
        AJArray ajArray = expectedArray(element);

//                var listWithGeneric = requestType.getGenericType();
//                Class actualType = null;
//                if (listWithGeneric instanceof ParameterizedType p) {
//                    if (p.getActualTypeArguments().length < 1)
//                        throw new AndreiJsonovichInternalException("Expected parameterized type");
//                    actualType = Class.forName(p.getActualTypeArguments()[0].getTypeName());
//                }
//
//                AJArray array = (AJArray) property.getValue();
//                List<Object> list = new ArrayList<>();
//                for (AJElement ajElement : array.getAll()) {
//                    list.add(context.toUserType(actualType, (AJObject) ajElement));
//                }
//
//                if (List.class.isAssignableFrom(field.getType()))
//                    field.set(instance, list);
//                else if (Set.class.isAssignableFrom(field.getType()))
//                    field.set(instance, new HashSet<>(list));
//                else if (field.getType().isArray())
//                    field.set(instance, list.toArray());
//                else throw new AndreiJsonovichInternalException("Unexpected behaviour");
        return null;
    }

    private <T> T toSet(Class<T> requestType, AJElement element, AJConvertorContext context) {
        return null;
    }

    private <T> T toArray(Class<T> requestType, AJElement element, AJConvertorContext context) {
        return null;
    }

    private <T> T toMap(Class<T> requestType, AJElement element, AJConvertorContext context) {
        return null;
    }

    private <T> T advanced(Class<T> requestType, AJElement element, AJConvertorContext context) {
        var deserializeClass = requestType.getName();
        var standartConvertor = CONVERTORS.get(deserializeClass);
        if (standartConvertor != null) {
            return (T) standartConvertor.toUserType(element, context);
        } else {
            return buildObject(requestType, element, context);
        }
    }

    private <T> T buildObject(Class<T> requestType, AJElement element, AJConvertorContext context) {
        try {
            T instance = requestType.newInstance();
            var fields = requestType.getDeclaredFields();
            for (Field field : fields) {
                var modifiers = field.getModifiers();
                if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) continue;

                field.setAccessible(true);
                var fieldType = field.getType().getName();
                var property = ((AJObject) element).getProperty(field.getName());
                var convertor = CONVERTORS.get(fieldType);

                if (convertor != null) {
                    field.set(instance, convertor.toUserType(property.getValue(), context));
                } else if (Object.class.isAssignableFrom(field.getType())) {
                    // if (genericType instanceof ParameterizedType) {
                    //    ParameterizedType parameterizedType = (ParameterizedType) genericType;
                    //    Type[] typeArguments = parameterizedType.getActualTypeArguments();
                    //    if (typeArguments.length > 0) {
                    //        Type elementType = typeArguments[0];
                    //        if (elementType instanceof Class) {
                    //            Class<?> someClass = (Class<?>) elementType;
                    //            // Теперь у вас есть класс Some
                    //            System.out.println("Тип элементов в списке: " + someClass.getName());
                    //        }
                    //    }
                    //}

//                    var ss = field.getGenericType().getType().getTypeName();
//                    System.out.println(ss);
                    field.set(instance, toUserType(field.getType(), property.getValue(), context));
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

    private AJArray expectedArray(AJElement element) {
        if (element.getType() != AJElement.Type.ARRAY)
            throw new ClassCastException("Expected ARRAY, got " + element.getType() + System.lineSeparator() + element.toString());
        return (AJArray) element;
    }

    //    private <T> void asObject(AJObject target, Field field, T object, AJConvertorContext context) throws
    //            IllegalAccessException {
    //        addProperty(target, field.getName(), context.toAJObject(field.get(object)));
    //    }
}