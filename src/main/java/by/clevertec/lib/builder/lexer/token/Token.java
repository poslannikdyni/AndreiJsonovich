package by.clevertec.lib.builder.lexer.token;


import by.clevertec.lib.builder.lexer.utility.RowColumnPosition;

public class Token{
    public final TokenType type;        // Ссылка на тип токена.
    public final String literal;        // Содержимое для литеральных токенов(имя переменной, функции, цифры числа).
    public final RowColumnPosition start; // Позиция токена в файле. Считается от начала файла.
    public final RowColumnPosition end; // Позиция токена в файле. Считается от начала файла.

    public Token(TokenType type, String literal, RowColumnPosition start, RowColumnPosition end) {
        this.type = type;
        this.literal = literal;
        this.start = start;
        this.end = end;
    }

    public Token(TokenType type, RowColumnPosition start, RowColumnPosition end) {
        this(type, "", start, end);
    }

    // Метод вернет текстовое содержимое токена либо его название.
    public String getLexeme() {
        if (!literal.isEmpty())
            return literal;
        return type.lexeme;
    }

    @Override
    public String toString() {
        return literal;
    }
}
