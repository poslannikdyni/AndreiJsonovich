package by.clevertec.lib.reflection.impl.collections;

import by.clevertec.lib.api.AJConvertorContext;
import by.clevertec.lib.intermediate_representation.AJArray;
import by.clevertec.lib.intermediate_representation.AJElement;
import by.clevertec.lib.intermediate_representation.AJPrimitive;
import by.clevertec.lib.reflection.TypeConvertor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ArrayListConvertor implements TypeConvertor<List> {
    @Override
    public Set<String> getAlias() {
        return Set.of(
                "java.until.List",
                "java.until.ArrayList"
        );
    }

    @Override
    public AJElement.Type expectedType() {
        return AJElement.Type.ARRAY;
    }

    @Override
    public AJPrimitive.ContentPresentation expectedPrimitiveContentPresentation() {
        return AJPrimitive.ContentPresentation.NOP;
    }

    @Override
    public AJElement toAJElement(List field, AJConvertorContext context){
        AJArray ajArray = new AJArray();
//        for (Object item : items) {
//            ajArray.add(context.toAJObject(item));
//        }

throw new RuntimeException("TODO"); //TODO
//        return new AJPrimitive(AJPrimitive.ContentPresentation.BOOLEAN, field.toString());
    }

    @Override
    public List toUserType(AJElement element, AJConvertorContext context){
//        var field = (AJPrimitive) element;
//        return Boolean.parseBoolean(field.getContent().toString());
        throw new RuntimeException("TODO"); //TODO
    }


    //    private <T> void asArray(AJObject target, Field field, T object, AJConvertorContext context) throws
//            IllegalAccessException {
//        Object[] items;
//        if (Collection.class.isAssignableFrom(field.getType())) {
//            items = ((Collection) field.get(object)).toArray();
//        } else if (field.getType().isArray()) {
//            items = (Object[]) field.get(object);
//        } else {
//            throw new RuntimeException("TODO"); //TODO
//        }
//
//        AJArray ajArray = new AJArray();
//        for (Object item : items) {
//            ajArray.add(context.toAJObject(item));
//        }
//
//        addProperty(target, field.getName(), ajArray);
//    }
}
