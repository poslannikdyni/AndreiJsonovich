package by.clevertec.lib.builder.lexer.utility;

public class RowColumnPosition  {
    private final int row;
    private final int column;
    private final int position;

    public RowColumnPosition(int row, int column, int position) {
        this.row = row;
        this.column = column;
        this.position = position;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getPosition() {
        return position;
    }

    public int getRowForUserPresent() {
        return row+1;
    }

    public int getColumnForUserPresent() {
        return column+1;
    }

    public int getPositionForUserPresent() {
        return position+1;
    }

}
