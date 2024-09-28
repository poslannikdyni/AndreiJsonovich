package by.clevertec.lib.builder.parser.parser;

import by.clevertec.lib.builder.lexer.TokenTypes;
import by.clevertec.lib.builder.lexer.token.Token;
import by.clevertec.lib.builder.lexer.token.TokenType;
import by.clevertec.lib.builder.lexer.utility.RowColumnPosition;
import by.clevertec.lib.builder.parser.exceptions.ExceptionInfo;
import by.clevertec.lib.builder.parser.exceptions.ParserExceptionBuilder;

import java.util.List;

import static by.clevertec.lib.builder.parser.utility.Utility.showAllTokenInRow;

public abstract class AbstractParser {
    protected String source;
    protected List<Token> tokens;
    protected int length;
    protected int position = 0;
    protected int countError;

    public AbstractParser(String source, List<Token> tokens) {
        this.source = source;
        this.tokens = tokens;
        this.length = tokens.size();
    }

    public String getSource() {
        return source;
    }

    public abstract Expr.JObject parse();

    public static final RowColumnPosition BEFORE_ZERO_POSITION_EOF_RCP = new RowColumnPosition(-101, -102, -103);
    public static final RowColumnPosition EOF_RCP = new RowColumnPosition(-1, -2, -3);
    protected Token EOF_LESS_0 = new Token(TokenTypes.getEofToken(), "Каретка указывает на значение меньше 0", BEFORE_ZERO_POSITION_EOF_RCP, BEFORE_ZERO_POSITION_EOF_RCP);
    protected Token EOF_MORE_LENGTH = new Token(TokenTypes.getEofToken(), "Каретка указывает на значение больше чем содержится в списке токенов ", EOF_RCP, EOF_RCP);

    protected Token peek(int relativePosition) {
        int pos = position + relativePosition;
        if (pos < 0) return EOF_LESS_0;
        if (pos >= length) return EOF_MORE_LENGTH;
        return tokens.get(pos);
    }

    protected Token peek() {
        return peek(0);
    }

    protected boolean peekB(TokenType type) {
        return peek(0).type.id == type.id;
    }

    protected Token peek_1() {
        return peek(-1);
    }

    protected Token peek1() {
        return peek(1);
    }


    protected Token next() {
        position++;
        return peek();
    }

    protected Token advance() {
        next();
        return peek_1();
    }

    protected boolean match(TokenType type) {
        if (peekB(type) == false) return false;
        next();
        return true;
    }

    public Token expected(TokenType symbol) {
        if (match(symbol)) return peek_1();

        ParserExceptionBuilder.expected(symbol, getSource(), getExceptionInfo());
        return null;
    }

    protected boolean isAtEnd() {
        return peekB(TokenTypes.getEofToken());
    }

    protected boolean notIsAtEnd() {
        return isAtEnd() == false;
    }

    protected ExceptionInfo getExceptionInfo() {
        return new ExceptionInfo(peek(), showAllTokenInRow(peek().start.getRow(), tokens));
    }
}


