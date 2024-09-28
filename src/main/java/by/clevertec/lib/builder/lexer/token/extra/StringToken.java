package by.clevertec.lib.builder.lexer.token.extra;


import by.clevertec.lib.builder.lexer.TokenTypes;
import by.clevertec.lib.builder.lexer.token.Token;
import by.clevertec.lib.builder.lexer.utility.RowColumnPosition;

public class StringToken extends Token {
    public StringToken(String fullText, RowColumnPosition start, RowColumnPosition end) {
        super(TokenTypes.TOKEN_STRING_LITERAL, fullText, start, end);
    }
}