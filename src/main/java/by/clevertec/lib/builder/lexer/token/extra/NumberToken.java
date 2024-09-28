package by.clevertec.lib.builder.lexer.token.extra;


import by.clevertec.lib.builder.lexer.TokenTypes;
import by.clevertec.lib.builder.lexer.token.Token;
import by.clevertec.lib.builder.lexer.utility.RowColumnPosition;

public class NumberToken extends Token {
    public final boolean point;

    public NumberToken(String inSourcePressent, boolean point,
                       RowColumnPosition start, RowColumnPosition end) {
        super(TokenTypes.TOKEN_NUMBER_LITERAL, inSourcePressent, start, end);
        this.point = point;
    }
}
