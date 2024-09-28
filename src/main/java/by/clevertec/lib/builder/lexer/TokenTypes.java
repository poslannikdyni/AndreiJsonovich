package by.clevertec.lib.builder.lexer;


import by.clevertec.lib.builder.lexer.token.TokenType;

import static by.clevertec.lib.builder.lexer.token.TokenGroup.KEYWORD;
import static by.clevertec.lib.builder.lexer.token.TokenGroup.LITERAL;
import static by.clevertec.lib.builder.lexer.token.TokenGroup.SERVICE_SYMBOLS;
import static by.clevertec.lib.builder.lexer.token.TokenGroup.SPACE;
import static by.clevertec.lib.builder.lexer.token.TokenGroup.SYMBOL;

public final class TokenTypes {
    public static final int TOKEN_TRUE_ID = 1;
    public static final TokenType TOKEN_TRUE = new TokenType(TOKEN_TRUE_ID, "TRUE", "true", KEYWORD);
    public static final int TOKEN_FALSE_ID = 2;
    public static final TokenType TOKEN_FALSE = new TokenType(TOKEN_FALSE_ID, "FALSE", "false", KEYWORD);
    public static final int TOKEN_NULL_ID = 3;
    public static final TokenType TOKEN_NULL = new TokenType(TOKEN_NULL_ID, "NULL", "null", KEYWORD);
    public static final int TOKEN_STRING_LITERAL_ID = 4;
    public static final TokenType TOKEN_STRING_LITERAL = new TokenType(TOKEN_STRING_LITERAL_ID, "STRING_LITERAL", LITERAL);
    public static final int TOKEN_NUMBER_LITERAL_ID = 5;
    public static final TokenType TOKEN_NUMBER_LITERAL = new TokenType(TOKEN_NUMBER_LITERAL_ID, "NUMBER_LITERAL", LITERAL);
    public static final int TOKEN_COLON_ID = 6;
    public static final TokenType TOKEN_COLON = new TokenType(TOKEN_COLON_ID, "COLON", ":", SYMBOL);
    public static final int TOKEN_KOSKA_ID = 7;
    public static final TokenType TOKEN_KOSKA = new TokenType(TOKEN_KOSKA_ID, "KOSKA", ",", SYMBOL);
    public static final int TOKEN_LEFT_BRACE_ID = 8;
    public static final TokenType TOKEN_LEFT_BRACE = new TokenType(TOKEN_LEFT_BRACE_ID, "LEFT_BRACE", "{", SYMBOL);
    public static final int TOKEN_RIGHT_BRACE_ID = 9;
    public static final TokenType TOKEN_RIGHT_BRACE = new TokenType(TOKEN_RIGHT_BRACE_ID, "RIGHT_BRACE", "}", SYMBOL);
    public static final int TOKEN_LEFT_BRACET_ID = 10;
    public static final TokenType TOKEN_LEFT_BRACET = new TokenType(TOKEN_LEFT_BRACET_ID, "LEFT_BRACET", "[", SYMBOL);
    public static final int TOKEN_RIGHT_BRACET_ID = 11;
    public static final TokenType TOKEN_RIGHT_BRACET = new TokenType(TOKEN_RIGHT_BRACET_ID, "RIGHT_BRACET", "]", SYMBOL);
    public static final int TOKEN_SPACE_SYMBOL_ID = 12;
    public static final TokenType TOKEN_SPACE_SYMBOL = new TokenType(TOKEN_SPACE_SYMBOL_ID, "SPACE_SYMBOL", " ", SPACE);
    public static final int TOKEN_TAB_SYMBOL_ID = 13;
    public static final TokenType TOKEN_TAB_SYMBOL = new TokenType(TOKEN_TAB_SYMBOL_ID, "TAB_SYMBOL", "  ", SPACE);
    public static final int TOKEN_NEW_LINE_ID = 14;
    public static final TokenType TOKEN_NEW_LINE = new TokenType(TOKEN_NEW_LINE_ID, "NEW_LINE", "\n", SPACE);
    public static final int TOKEN_R_SYMBOL_ID = 15;
    public static final TokenType TOKEN_R_SYMBOL = new TokenType(TOKEN_R_SYMBOL_ID, "R_SYMBOL", "\r", SPACE);
    public static final int TOKEN_ERROR_ID = 16;
    public static final TokenType TOKEN_ERROR = new TokenType(TOKEN_ERROR_ID, "ERROR", SERVICE_SYMBOLS);
    public static final int TOKEN_EOF_ID = 17;
    public static final TokenType TOKEN_EOF = new TokenType(TOKEN_EOF_ID, "EOF", SERVICE_SYMBOLS);

    // Получает ключевое слово по содержимому.
   // Также можно запросить и оператор, главное что бы данный токен присутствовал в языке.
    public static TokenType getKeyWords(String text) {
        return switch (text) {
            case "true" -> TOKEN_TRUE;
            case "false" -> TOKEN_FALSE;
            case "null" -> TOKEN_NULL;
            default -> null;
        };
    }

    // Получает массив операторов начинающихся с symbol.
    public static TokenType getSymbol(char symbol) {
        return switch (symbol) {
            case ':' -> TOKEN_COLON;
            case '{' -> TOKEN_LEFT_BRACE;
            case '[' -> TOKEN_LEFT_BRACET;
            case ',' -> TOKEN_KOSKA;
            case '}' -> TOKEN_RIGHT_BRACE;
            case ']' -> TOKEN_RIGHT_BRACET;
            default -> null;
        };
    }

    public static TokenType getEofToken() {
        return TOKEN_EOF;
    }
}
