package by.clevertec.lib.builder.parser.exceptions;

import by.clevertec.lib.builder.lexer.token.TokenType;
import by.clevertec.lib.builder.parser.parser.Expr;

import static by.clevertec.lib.builder.lexer.utility.StringUtility.getPointerToIn;
import static by.clevertec.lib.builder.lexer.utility.StringUtility.getStringBetweenNewLineSymbol;
import static by.clevertec.lib.builder.lexer.utility.StringUtility.ls;

public class ParserExceptionBuilder {

    public static class ParserException extends RuntimeException{
        public ParserException(String message) {
            super(message);
        }
    }

    private static String buildFullRepresentation(String source, ExceptionInfo info, String msgRaw) {
        return getHeader(info) +
                msgRaw + ls +
                getStringBetweenNewLineSymbol(source, info.peekToken.start.getPosition()) + ls +
                getPointerToIn(source, info.peekToken.start.getPosition())
                ;
    }

    private static String getHeader(ExceptionInfo info) {
        return String.format("Ошибка синтаксического разбора. " +
                        "Строка = %s столбец = %s токен = [%s]%s",
                info.peekToken.start.getRowForUserPresent(), info.peekToken.start.getColumnForUserPresent(), info.peekToken.getLexeme(), ls
        );
    }

    public static ParserException expected(TokenType type, String source, ExceptionInfo info) {
        var message = String.format("Ожидался токен : %s. Обнаружен токен : %s",type.lexeme, info.getPeekToken().getLexeme());
        throw new ParserException(buildFullRepresentation(source, info,message));
    }

    public static Expr unknownLiteral(String source, ExceptionInfo info) {
        var message = String.format("Недопустимый токен : %s", info.getPeekToken().getLexeme());
        throw new ParserException(buildFullRepresentation(source, info,message));
    }
}
