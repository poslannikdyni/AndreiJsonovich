package by.clevertec.lib;

import by.clevertec.lib.api.AJConvertor;
import by.clevertec.lib.builder.AJElementBuilder;
import by.clevertec.lib.builder.lexer.lexer.Lexer;
import by.clevertec.lib.builder.parser.parser.Expr;
import by.clevertec.lib.builder.parser.parser.Parser;
import by.clevertec.lib.intermediate_representation.AJObject;
import by.clevertec.lib.printer.AJPrinter;

public class Jsonovich {
    private JsonovichContext context = new JsonovichContext();

    public void register(AJConvertor processor) {
        context.register(processor);
    }

    public <T> String buildJson(T object) {
        AJObject jObject = context.toAJObject(object);
        return new AJPrinter().print(jObject);
    }

    public <T> T buildObject(Class<T> clazz, String json) {
        Lexer lexer = new Lexer(json);
        var tokens = lexer.scanTokens();

        Parser parser = new Parser(json, tokens);
        Expr.JObject exprObject = parser.parse();
        AJObject jObject = (AJObject) exprObject.accept(new AJElementBuilder());

        return context.toUserType(clazz, jObject);
    }

}