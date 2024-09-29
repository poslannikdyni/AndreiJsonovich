package by.clevertec.lib.intermediate_representation;

import by.clevertec.lib.exceptions.AndreiJsonovichInternalException;

public class AJProperty implements AJElement {
    private String name;
    private AJElement value;

    public AJProperty(String name, AJElement value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public Type getType() {
        return Type.PROPERTY;
    }

    public String getName() {
        return name;
    }

    public AJElement getValue() {
        return value;
    }

    public static class AJPropertyBuilder {
        private String name;
        private AJElement value;

        public AJPropertyBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public AJPropertyBuilder setValue(AJElement value) {
            this.value = value;
            return this;
        }

        public AJProperty build(){
            if(name == null || value == null) throw new NullPointerException("AJPropertyBuilder must has not null fields name and value");
            return new AJProperty(name, value);
        }
    }

    public static AJPropertyBuilder builder() {
        return new AJPropertyBuilder();
    }
}

