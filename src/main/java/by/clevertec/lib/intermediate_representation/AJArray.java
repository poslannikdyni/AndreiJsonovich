package by.clevertec.lib.intermediate_representation;

import java.util.ArrayList;
import java.util.List;

public class AJArray implements AJElement {
    protected List<AJElement> content = new ArrayList<>();

    @Override
    public Type getType() {
        return Type.ARRAY;
    }

    public void add(AJElement element) {
        content.add(element);
    }

    public List<AJElement> getAll() {
        return content;
    }
}
