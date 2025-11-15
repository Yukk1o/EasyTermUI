package io.yukk1o.easytermui.util;

import static io.yukk1o.easytermui.EasyTermUI.writer;

public class PrintUtils {
    private PrintUtils() {}

    // ==================== 绝对坐标打印 ====================
    public static  void  printAt(int row, int col, String text) {
        AnsiUtils.moveCursor(row, col);
        writer.write(text);
    }
}
