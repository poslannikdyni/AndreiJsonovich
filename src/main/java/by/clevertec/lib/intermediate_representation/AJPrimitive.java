package by.clevertec.lib.intermediate_representation;

public class AJPrimitive implements AJElement{
    public enum ContentPresentation {
        STRING,
        BOOLEAN,
        LONG,
        DOUBLE,
        NULL;
    }

    private ContentPresentation presentation;
    private Object content;

    public AJPrimitive(ContentPresentation presentation, Object content) {
        this.presentation = presentation;
        this.content = content;
    }

    @Override
    public Type getType() {
        return Type.PRIMITIVE;
    }

    public ContentPresentation getPresentation(){
        return presentation;
    }

    public Object getContent() {
        return content;
    }
}
