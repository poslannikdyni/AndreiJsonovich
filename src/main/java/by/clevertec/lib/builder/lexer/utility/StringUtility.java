package by.clevertec.lib.builder.lexer.utility;

public class StringUtility {
    public static final String ls = System.lineSeparator();
    public static final char NEW_ROW_SYMBOL = '\n';
    public static final char EOF_SYMBOL = '\0';

    /**
     * Возвращает символ пробела повторённый несколько раз.
     *
     * @param count количество повторов символа
     */
    public static String space(int count) {
        return " ".repeat(count);
    }

    /**
     * Возвращает подстроку между символами новой строки.
     *
     * @param content строка в которой выделяем подстроку
     * @param pos     позиция от которой вправо и влево ищем символы новой строки
     * @return подстрока между семволами новой строки
     */
    public static String getStringBetweenNewLineSymbol(String content, int pos) {
        if (pos >= content.length()) return "";
        int startString = findNewLineSymbolBeforePos(content, pos) + 1;
        int endString = findNewLineSymbolAfterPos(content, pos);
        return content.substring(startString, endString);
    }

    /**
     * Возвращает индекс символа новой строки слева от индекса pos
     *
     * @param content строка в которой ищем символ новой строки
     * @param pos     позиция от которой ищем влево
     * @return индекс символа новой строки
     */
    public static int findNewLineSymbolBeforePos(String content, int pos) {
        if (pos - 1 < 0) return 0;
        if (pos >= content.length()) return content.length();
        for (int i = pos - 1; i >= 0; i--) {
            if (content.charAt(i) == NEW_ROW_SYMBOL) return i;
        }
        return 0;
    }

    /**
     * Аналогично методу {@link #findNewLineSymbolBeforePos} за тем исключением что поиск выполняется справа от pos
     */
    public static int findNewLineSymbolAfterPos(String content, int pos) {
        if (pos + 1 >= content.length()) return content.length();
        for (int i = pos + 1; i < content.length(); i++) {
            if (content.charAt(i) == NEW_ROW_SYMBOL) return i;
        }
        return content.length() - 1;
    }

    /**
     * Возвращает указатель на определенную позицию в строке.<br>
     * Пример:<br>
     * Мама мыла раму.<br>
     * ....|---
     *
     * @param content строка на которую формируется указатель
     * @param pos     позиция для указателя
     */
    public static String getPointerToIn(String content, int pos) {
        int stringStart = findNewLineSymbolBeforePos(content, pos) +1 ;
        int count = pos - stringStart; // -1
        if (count > 0)
            return space(pos - stringStart) + "|---";
        else
            return "|---";
    }

    /**
     * Возвращает указатель на определенную позицию в строке.<br>
     * Пример:<br>
     * Мама мыла раму.<br>
     * .....|---
     *
     * @param pos позиция для указателя
     */
    public static String getPointerToIn(int pos) {
        int count = pos - 1;
        if (count > 0)
            return space(count) + "|---";
        else
            return "|---";
    }
}

