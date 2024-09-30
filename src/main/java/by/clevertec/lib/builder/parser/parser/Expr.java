package by.clevertec.lib.builder.parser.parser;


import by.clevertec.lib.builder.lexer.token.Token;
import by.clevertec.lib.builder.lexer.token.extra.StringToken;

import java.util.List;

public abstract class Expr {
    public abstract <R> R accept(Visitor<R> visitor);

    public interface Visitor<R> {
        R visitArrayExpr(Array array);
        R visitJObjectExpr(JObject object);
        R visitPropertyExpr(Property property);
        R visitLiteralExpr(Literal literal);
    }

    public static class Array extends Expr {
        public final List<Expr> objects;

        public Array(List<Expr> objects) {
            this.objects = objects;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitArrayExpr(this);
        }
    }

    public static class JObject extends Expr {
        public final List<Property> properties;

        public JObject(List<Property> properties) {
            this.properties = properties;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitJObjectExpr(this);
        }
    }

    public static class Property extends Expr {
        public final StringToken propertyName;
        public final Expr value;

        public Property(StringToken propertyName, Expr value) {
            this.propertyName = propertyName;
            this.value = value;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitPropertyExpr(this);
        }
    }

    public static class Literal extends Expr {
        public final Token value;

        public Literal(Token value) {
            this.value = value;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitLiteralExpr(this);
        }
    }
}

