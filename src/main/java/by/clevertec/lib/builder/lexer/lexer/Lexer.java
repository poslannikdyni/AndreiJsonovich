package by.clevertec.lib.builder.lexer.lexer;

import by.clevertec.lib.builder.lexer.TokenTypes;
import by.clevertec.lib.builder.lexer.exception.ExceptionBuilder;
import by.clevertec.lib.builder.lexer.token.Token;
import by.clevertec.lib.builder.lexer.token.TokenType;
import by.clevertec.lib.builder.lexer.token.extra.NumberToken;
import by.clevertec.lib.builder.lexer.token.extra.StringToken;

import java.util.List;

public class Lexer extends AbstractLexer {

    public Lexer(String source) {
        super(source);
    }

    // Основной метод работы
    @Override
    public List<Token> scanTokens() {
        while (notIsAtEnd()) {
            rememberPositionWhereStartToken();
            char symbol = peek(); // Получаем текущий символ.

            if (isAlpha(symbol)) { // Ключевые слова языка, идентификатор.
                tokenizeWord();

            } else if (isDecDigit(symbol)) { // Цифры.
                tokenizeDigit();

            } else if (symbol == STRING_START) { // Строки.
                tokenizeString();

            } else if (TokenTypes.getSymbol(symbol) != null) { // Операторы.
                tokenizeSymbol(symbol);

            } else if (isEmptySymbol(symbol)) { // Пробелы, табы и переводы строки.
                next();

            } else {
                // Здесь остаются только символы не используемые в операторах.
                // Если встретили незнакомый символ - обрабатываем эту ошибку.
                ExceptionBuilder.unknownSymbol(getSource(), getExceptionInfo());
            }
        }

        addToken(buildEofToken());
        return getTokens();
    }

    // Проводим разбор слова состоящего из букв цифр и символов _
    protected void tokenizeWord() {
        char symbol;
        do {
            symbol = next();
        } while (isAlpha(symbol));

        String text = fromStartToCurrentPosition();
        TokenType type = TokenTypes.getKeyWords(text);
        if (type == null) ExceptionBuilder.invalidKeyWord(text, getSource(), getExceptionInfo());
        addToken(type, text);
    }

    // Метод создает цифроввые литералы.
    protected void tokenizeDigit() {
        boolean point = false;
        whileNumber();
        if (peekB('.')) {
            point = true;
            next();
            whileNumber();
        }

        addToken(new NumberToken(fromStartToCurrentPosition(), point,
                getStartRowColumnPosition(), getCurrentRowColumnPosition()
        ));
    }

    protected void whileNumber() {
        while (isDecDigit(next())) {
            //NOP
        }
    }

    protected void tokenizeSymbol(char symbol) {
        next();
        addToken(TokenTypes.getSymbol(symbol));
    }

    private static final char STRING_START = '\"';

    private void tokenizeString() {
        var symbol = next();
        String text;
        if(symbol != STRING_START) {
            whileNotPresent(STRING_START, true);
            text = fromStartToCurrentPosition();
            if (text.length() > 2)
                text = text.substring(1, text.length() - 1);
        }else {
            next();
            text = "";
        }
        addToken(new StringToken(text, getStartRowColumnPosition(), getCurrentRowColumnPosition()));
    }
}
