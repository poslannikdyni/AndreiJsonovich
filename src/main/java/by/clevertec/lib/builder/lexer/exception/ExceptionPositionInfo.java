package by.clevertec.lib.builder.lexer.exception;


import by.clevertec.lib.builder.lexer.utility.RowColumnPosition;

public class ExceptionPositionInfo {
    public final RowColumnPosition start;
    public final RowColumnPosition current;
    public final char peekSymbol;

    public ExceptionPositionInfo(RowColumnPosition start, RowColumnPosition current, char peek) {
        this.start = start;
        this.current = current;
        this.peekSymbol = peek;
    }
}
