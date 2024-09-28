package by.clevertec.lib.builder.parser.parser;


import by.clevertec.lib.builder.lexer.token.Token;
import by.clevertec.lib.builder.lexer.token.extra.StringToken;
import by.clevertec.lib.builder.parser.exceptions.ParserExceptionBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static by.clevertec.lib.builder.lexer.TokenTypes.TOKEN_COLON;
import static by.clevertec.lib.builder.lexer.TokenTypes.TOKEN_COLON_ID;
import static by.clevertec.lib.builder.lexer.TokenTypes.TOKEN_FALSE_ID;
import static by.clevertec.lib.builder.lexer.TokenTypes.TOKEN_KOSKA;
import static by.clevertec.lib.builder.lexer.TokenTypes.TOKEN_LEFT_BRACE;
import static by.clevertec.lib.builder.lexer.TokenTypes.TOKEN_LEFT_BRACET;
import static by.clevertec.lib.builder.lexer.TokenTypes.TOKEN_LEFT_BRACET_ID;
import static by.clevertec.lib.builder.lexer.TokenTypes.TOKEN_LEFT_BRACE_ID;
import static by.clevertec.lib.builder.lexer.TokenTypes.TOKEN_NULL_ID;
import static by.clevertec.lib.builder.lexer.TokenTypes.TOKEN_NUMBER_LITERAL_ID;
import static by.clevertec.lib.builder.lexer.TokenTypes.TOKEN_RIGHT_BRACE;
import static by.clevertec.lib.builder.lexer.TokenTypes.TOKEN_RIGHT_BRACET;
import static by.clevertec.lib.builder.lexer.TokenTypes.TOKEN_STRING_LITERAL;
import static by.clevertec.lib.builder.lexer.TokenTypes.TOKEN_STRING_LITERAL_ID;
import static by.clevertec.lib.builder.lexer.TokenTypes.TOKEN_TRUE_ID;

public class Parser extends AbstractParser {

    public Parser(String source, List<Token> tokens) {
        super(source, tokens);
    }

    @Override
    public Expr.JObject parse() {
        return parseObject();
    }

    private Expr.JObject parseObject() {
        expected(TOKEN_LEFT_BRACE);
        List<Expr.Property> properties = new ArrayList<>();
        while (notIsAtEnd()) {
            properties.add(parseProperty());
            if (match(TOKEN_KOSKA) == false) break;
        }
        expected(TOKEN_RIGHT_BRACE);
        return new Expr.JObject(properties);
    }

    private Expr.Property parseProperty() {
        var name = expected(TOKEN_STRING_LITERAL);
        expected(TOKEN_COLON);
        var value = switch (peek().type.id) {
            case TOKEN_LEFT_BRACE_ID -> parseObject();
            case TOKEN_LEFT_BRACET_ID -> parseArray();
            default -> parseLiteral();
        };
        return new Expr.Property((StringToken) name, value);
    }

    private Expr parseArray() {
        expected(TOKEN_LEFT_BRACET);
        if (match(TOKEN_RIGHT_BRACET)) return new Expr.Array(Collections.emptyList());

        List<Expr> items = new ArrayList<>();
        while (notIsAtEnd()) {
            var tokenId = peek().type.id;
            if (tokenId == TOKEN_LEFT_BRACE_ID)
                items.add(parseObject());
            else if (tokenId == TOKEN_LEFT_BRACET_ID)
                items.add(parseArray());
            else if (tokenId == TOKEN_STRING_LITERAL_ID && peek1().type.id == TOKEN_COLON_ID)
                items.add(parseProperty());
            else
                items.add(parseLiteral());

            if (match(TOKEN_KOSKA) == false) break;
        }
        expected(TOKEN_RIGHT_BRACET);
        return new Expr.Array(items);
    }

    private Expr parseLiteral() {
        return switch (peek().type.id) {
            case TOKEN_NULL_ID,
                 TOKEN_FALSE_ID,
                 TOKEN_TRUE_ID,
                 TOKEN_STRING_LITERAL_ID,
                 TOKEN_NUMBER_LITERAL_ID -> new Expr.Literal(advance());
            default ->   ParserExceptionBuilder.unknownLiteral(getSource(), getExceptionInfo());
        };
    }
}

