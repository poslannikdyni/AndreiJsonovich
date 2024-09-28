package by.clevertec.lib.builder.parser.utility;

import by.clevertec.lib.builder.lexer.token.Token;

import java.util.ArrayList;
import java.util.List;


public class Utility {
    public static List<Token> showAllTokenInRow(int row, List<Token> tokens) {
        List<Token> rez = new ArrayList<>();
        for (Token token : tokens) {
            if (token.end.getRow() == row || token.start.getRow() == row)
                rez.add(token);

            if (token.end.getRow() > row)
                break;
        }
        return rez;
    }
}
