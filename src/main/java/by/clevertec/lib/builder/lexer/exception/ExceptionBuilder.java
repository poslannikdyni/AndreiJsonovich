package by.clevertec.lib.builder.lexer.exception;

import static by.clevertec.lib.builder.lexer.token.SkriningSymbol.getSymbolAsString;
import static by.clevertec.lib.builder.lexer.utility.StringUtility.getPointerToIn;
import static by.clevertec.lib.builder.lexer.utility.StringUtility.getStringBetweenNewLineSymbol;

public class ExceptionBuilder {
    public static class LexerException extends RuntimeException{
        public LexerException(String message) {
            super(message);
        }
    }

    private static String buildFullRepresentation(String source, ExceptionPositionInfo info, String msgRaw) {
        return getHeader(info) + System.lineSeparator() +
                msgRaw + System.lineSeparator() +
                getStringBetweenNewLineSymbol(source, info.current.getPosition()) + System.lineSeparator() +
                getPointerToIn(source, info.current.getPosition())
                ;
    }

    private static String getHeader(ExceptionPositionInfo info) {
        return String.format("Ошибка лексического разбора. " +
                        "Строка = %s столбец = %s%s",
                info.current.getRowForUserPresent(), info.current.getColumnForUserPresent(), System.lineSeparator()
        );
    }

    public static void unknownSymbol(String source, ExceptionPositionInfo info) {
        throw new LexerException(
                buildFullRepresentation(source, info,
                        String.format("Неизвестный символ [%s].",
                                getSymbolAsString(info.peekSymbol)
                        )
                )
        );
    }

    public static void invalidKeyWord(String text, String source, ExceptionPositionInfo info) {
        throw new LexerException(
                buildFullRepresentation(source, info,
                        String.format("Формат JSON может включать в себя ключевые слова - true, false, null. Обнаружено - %s", text)
                )
        );
    }
}
