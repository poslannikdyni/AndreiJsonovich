package by.clevertec.lib.builder.lexer.lexer;

import by.clevertec.lib.builder.lexer.exception.ExceptionPositionInfo;
import by.clevertec.lib.builder.lexer.token.Token;
import by.clevertec.lib.builder.lexer.token.TokenType;
import by.clevertec.lib.builder.lexer.utility.RowColumnPosition;

import java.util.ArrayList;
import java.util.List;

import static by.clevertec.lib.builder.lexer.TokenTypes.TOKEN_EOF;
import static by.clevertec.lib.builder.lexer.utility.StringUtility.EOF_SYMBOL;
import static by.clevertec.lib.builder.lexer.utility.StringUtility.NEW_ROW_SYMBOL;

public abstract class AbstractLexer{
    private final String source;            // Исходник, строка для лексического разбора.
    protected final int length;             // Длинна исходника в символах.
    private final List<Token> tokens;       // Результат.

    protected int position; // Текущая позиция. Считается с 0.
    protected int row, col; // Текущая строка, столбец.

    // Указывают на начало формирования токена :
    protected int startTokenPosition;  // Токен начинается в позиции ... .
    protected int startTokenRow;       // Токен начинается в строке ... .
    protected int startTokenCol;       // Токен начинается в столбце ... .

    public AbstractLexer(String source) {
        this.source = source;
        this.length = source.length();
        this.tokens = new ArrayList<>();
    }

    public String getSource() {
        return source;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public abstract List<Token> scanTokens();

    protected char peek(int relativePosition) {
        int pos = position + relativePosition;
        if (pos >= length) return EOF_SYMBOL;
        if (pos < 0) return EOF_SYMBOL;
        return source.charAt(pos);
    }

    // Методы просмотра символов.
    protected char peek() {
        return peek(0);
    }

    protected boolean peekB(char symbol) {
        return peek(0) == symbol;
    }

    private boolean previousSymbolIsNewLine = false;

    // Сдвиг каретки на следующий символ.
    protected char next() {
        position++;
        col++;
        var currentSymbol = peek();
        var currentSymbolIsNewLine = (currentSymbol == NEW_ROW_SYMBOL);

        if (currentSymbolIsNewLine) {
            previousSymbolIsNewLine = currentSymbolIsNewLine;
        } else if (previousSymbolIsNewLine) {
            col = 0;
            row++;
            previousSymbolIsNewLine = currentSymbolIsNewLine;
        }
        return currentSymbol;
    }

    protected void whileNotPresent(char symbol, boolean skip) {
        whileNotPresent(symbol, EOF_SYMBOL, EOF_SYMBOL, skip);
    }

    // Пока не конец файла или не встретился символ.
    protected void whileNotPresent(char symbol1, char symbol2, char symbol3, boolean skip) {
        var currentSymbol = peek();
        if (currentSymbol == symbol1 || currentSymbol == symbol2 || currentSymbol == symbol3) return;

        do{
            currentSymbol = next();
        }while (currentSymbol != symbol1 && currentSymbol != symbol2 && currentSymbol != symbol3);
        if (skip) next();
    }

    // Не достигнут конец строки.
    protected boolean notIsAtEnd() {
        return position < length;
    }

    protected static boolean isAlpha(char symbol) {
        return ('a' <= symbol && symbol <= 'z') ||
                ('A' <= symbol && symbol <= 'Z');
    }

    protected static boolean isDecDigit(char symbol) {
        return '0' <= symbol && symbol <= '9';
    }

    protected static boolean isEmptySymbol(char symbol) {
        return symbol == ' ' ||
                symbol == '\t' ||
                symbol == '\r' ||
                symbol == NEW_ROW_SYMBOL;
    }

    protected Token addToken(TokenType type) {
        Token rez = new Token(type, getStartRowColumnPosition(), getCurrentRowColumnPosition());
        tokens.add(rez);
        return rez;
    }

    protected Token addToken(TokenType type, String text) {
        Token rez = new Token(type, text, getStartRowColumnPosition(), getCurrentRowColumnPosition());
        tokens.add(rez);
        return rez;
    }

    protected Token addToken(Token token) {
        tokens.add(token);
        return token;
    }

    protected String fromStartToCurrentPosition() {
        return source.substring(startTokenPosition, position);
    }

    protected String string(int start, int end) {
        return source.substring(start, end);
    }

    // Запоминаем координаты в которых начинается новый токен :
    protected void rememberPositionWhereStartToken() {
        startTokenRow = row;  // Строки.
        startTokenCol = col;  // Столбца.
        startTokenPosition = position;  // Позиции в которой начато формирование токена.
    }

    protected RowColumnPosition getStartRowColumnPosition() {
        return new RowColumnPosition(startTokenRow, startTokenCol, startTokenPosition);
    }

    protected RowColumnPosition getCurrentRowColumnPosition() {
        return new RowColumnPosition(row, col, position);
    }

    protected Token buildEofToken() {
        var end = new RowColumnPosition(startTokenRow, startTokenCol + 1, startTokenPosition + 1);
        return new Token(TOKEN_EOF, end, end);
    }

    protected ExceptionPositionInfo getExceptionInfo() {
        return new ExceptionPositionInfo(
                new RowColumnPosition(startTokenRow, startTokenCol, startTokenPosition),
                new RowColumnPosition(row, col, position),
                peek());
    }
}
