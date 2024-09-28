package by.clevertec.lib.builder.parser.exceptions;

import by.clevertec.lib.builder.lexer.token.Token;

import java.util.List;

public class ExceptionInfo {
    public final Token peekToken;
    public final List<Token> tokensInRow;

    public ExceptionInfo(Token peekToken, List<Token> tokensInRow) {
        this.peekToken = peekToken;
        this.tokensInRow = tokensInRow;
    }

    public Token getPeekToken() {
        return peekToken;
    }

}
