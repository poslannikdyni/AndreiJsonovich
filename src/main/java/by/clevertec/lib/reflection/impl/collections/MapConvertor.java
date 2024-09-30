package by.clevertec.lib.reflection.impl.collections;

public class MapConvertor {
}
//    private <T> void asMapArray(AJObject target, Field field, T object, AJConvertorContext context) throws
//            IllegalAccessException {
//        Map<Object, Object> map = (Map<Object, Object>) field.get(object);
//        AJArray ajArray = new AJArray();
//        for (var item : map.entrySet()) {
//            var key = toAJObject(item.getKey(), context);
//            var value = toAJObject(item.getValue(), context);
//            AJArray itemArray = new AJArray();
//            itemArray.add(key);
//            itemArray.add(value);
//            ajArray.add(itemArray);
//        }
//
//        addProperty(target, field.getName(), ajArray);
//    }