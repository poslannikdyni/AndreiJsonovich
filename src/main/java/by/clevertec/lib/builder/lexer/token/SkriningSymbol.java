package by.clevertec.lib.builder.lexer.token;

import static by.clevertec.lib.builder.lexer.utility.StringUtility.EOF_SYMBOL;
import static by.clevertec.lib.builder.lexer.utility.StringUtility.NEW_ROW_SYMBOL;

public enum SkriningSymbol {
    SYMBOL_N(NEW_ROW_SYMBOL),
    SYMBOL_R('\r'),
    SYMBOL_T('\t'),
    SYMBOL_KOSKA('\''),
    SYMBOL_DVE_KOSKI('\"'),
    SYMBOL_O(EOF_SYMBOL),
    SYMBOL_B('\b'),
    SYMBOL_F('\f'),
    BAD_SKRINING_SYMBOL('A');

    public final Character symbol;

    SkriningSymbol(char symbol) {
        this.symbol = symbol;
    }

    public static final String getSymbolAsString(char symbol){
        switch (symbol) {
            case 'n' ->  {return "\\n";}
            case 'r' ->  {return "\\r";}
            case 't' ->  {return "\\t";}
            case '\'' -> {return "\\'";}
            case '\"' ->  {return "\"";}
            case '0' ->  {return "\\0";}
            case 'b' ->  {return "\\b";}
            case 'f' ->  {return "\\f";}
            default -> {
                return String.valueOf(symbol);
            }
        }
    }

    @Override
    public String toString() {
      return symbol.toString();
    }
}
