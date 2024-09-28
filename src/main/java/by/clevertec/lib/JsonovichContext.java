package by.clevertec.lib;

import by.clevertec.lib.api.AJConvertor;
import by.clevertec.lib.api.AJConvertorContext;
import by.clevertec.lib.intermediate_representation.AJObject;

import java.util.HashMap;
import java.util.Map;

public class JsonovichContext implements AJConvertorContext {
    private final ReflectionConvertor reflectionProcessor = new ReflectionConvertor();
    private Map<Class, AJConvertor> processors = new HashMap<>();

    public void register(AJConvertor processor) {
        processors.put(processor.forType(), processor);
    }

    @Override
    public <T> AJObject toAJObject(T object) {
        if (processors.containsKey(object.getClass()))
            return processors.get(object.getClass()).toAJObject(object, this);

        return reflectionProcessor.toAJObject(object, this);
    }

    @Override
    public <T> T toUserType(Class<T> clazz, AJObject object) {
        if (processors.containsKey(clazz))
            return (T) processors.get(clazz).toUserType(object, this);

        return reflectionProcessor.toUserType(clazz, object, this);
    }
}

