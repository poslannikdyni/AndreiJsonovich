package by.clevertec.lib.builder.lexer.token;

// Тип токена.
public class TokenType {
    public final int id;        // Уникальный идентификатор.
    public final String name;   // Имя токена используемое внутри программы.
    public final String lexeme; // Лексема токена(для литералов - задаётся пользователем, для всего остального - определенна внутри лексера).
    public final TokenGroup tokenGroup;   // Метка того чем является токен(Группа объединяющая несколько типов токена).

    public TokenType(int id, String name, String lexeme, TokenGroup tokenGroup) {
        this.id = id;
        this.lexeme = lexeme;
        this.name = name;
        this.tokenGroup = tokenGroup;
    }

    public TokenType(int id, String name, TokenGroup tokenGroup) {
        this(id, name, "", tokenGroup);
    }
}




