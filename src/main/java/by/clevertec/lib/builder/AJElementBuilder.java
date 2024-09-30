package by.clevertec.lib.builder;

import by.clevertec.lib.builder.lexer.token.extra.NumberToken;
import by.clevertec.lib.builder.parser.parser.Expr;
import by.clevertec.lib.exceptions.AndreiJsonovichInternalException;
import by.clevertec.lib.intermediate_representation.AJArray;
import by.clevertec.lib.intermediate_representation.AJElement;
import by.clevertec.lib.intermediate_representation.AJObject;
import by.clevertec.lib.intermediate_representation.AJPrimitive;
import by.clevertec.lib.intermediate_representation.AJProperty;

import static by.clevertec.lib.builder.lexer.TokenTypes.TOKEN_FALSE_ID;
import static by.clevertec.lib.builder.lexer.TokenTypes.TOKEN_NULL_ID;
import static by.clevertec.lib.builder.lexer.TokenTypes.TOKEN_NUMBER_LITERAL_ID;
import static by.clevertec.lib.builder.lexer.TokenTypes.TOKEN_STRING_LITERAL_ID;
import static by.clevertec.lib.builder.lexer.TokenTypes.TOKEN_TRUE_ID;

public class AJElementBuilder implements Expr.Visitor<AJElement> {
    @Override
    public AJElement visitArrayExpr(Expr.Array array) {
        AJArray rez = new AJArray();
        for (Expr item : array.objects) {
            rez.add(item.accept(this));
        }
        return rez;
    }

    @Override
    public AJElement visitJObjectExpr(Expr.JObject object) {
        AJObject rez = new AJObject();
        for (Expr.Property property : object.properties) {
            rez.addProperty((AJProperty) visitPropertyExpr(property));
        }
        return rez;
    }

    @Override
    public AJElement visitPropertyExpr(Expr.Property property) {
        return new AJProperty(property.propertyName.toString(), property.value.accept(this));
    }

    @Override
    public AJElement visitLiteralExpr(Expr.Literal literal) {
        var contentPresentation = getContentPresentation(literal);
        var value = getValueByContentPresentation(contentPresentation, literal);
        return new AJPrimitive(getContentPresentation(literal), value);
    }

    private AJPrimitive.ContentPresentation getContentPresentation(Expr.Literal literal) {
        return switch (literal.value.type.id) {
            case TOKEN_NULL_ID -> AJPrimitive.ContentPresentation.NULL;
            case TOKEN_FALSE_ID, TOKEN_TRUE_ID -> AJPrimitive.ContentPresentation.BOOLEAN;
            case TOKEN_STRING_LITERAL_ID -> AJPrimitive.ContentPresentation.STRING;
            case TOKEN_NUMBER_LITERAL_ID -> {
                NumberToken number = (NumberToken) literal.value;
                if (number.point) yield AJPrimitive.ContentPresentation.DOUBLE;
                else yield AJPrimitive.ContentPresentation.LONG;
            }
            default -> throw new IllegalArgumentException("Unrecognized token type: " + literal.value.type.id);
        };
    }

    private Object getValueByContentPresentation(AJPrimitive.ContentPresentation contentPresentation, Expr.Literal literal) {
        return switch (contentPresentation) {
            case STRING -> literal.value.toString();
            case BOOLEAN -> Boolean.parseBoolean(literal.value.toString());
            case LONG -> Long.parseLong(literal.value.toString());
            case DOUBLE -> Double.parseDouble(literal.value.toString());
            case NULL -> null;
            case NOP -> throw new RuntimeException("TODO"); //TODO;
        };
    }
}
