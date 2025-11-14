package io.yukk1o.easytermui.util;

import io.yukk1o.easytermui.constant.AnsiConstants;
import org.jline.terminal.Terminal;

import static io.yukk1o.easytermui.EasyTermUI.terminal;

public class AnsiUtils {
    // ================================== 光标动态操作 ==================================

    // 私有构造器：禁止实例化（纯工具类）
    private AnsiUtils() {
    }

    /**
     * 隐藏光标
     */
    public static void cursorHide() {
        executeTerminalOperation(() -> terminal.writer().write(AnsiConstants.CURSOR_HIDE));
    }

    /**
     * 显示光标
     */
    public static void cursorShow() {
        executeTerminalOperation(() -> terminal.writer().write(AnsiConstants.CURSOR_SHOW));
    }

    /**
     * 移动光标到指定坐标
     *
     * @param row 目标行（y轴）
     * @param col 目标列（x轴）
     */
    public static void moveCursor(int row, int col) {
        executeTerminalOperation(() -> {
            if (row <= 0 || col <= 0) {
                throw new IndexOutOfBoundsException("Row or col is illegal value");
            }
            terminal.writer().write(AnsiConstants.ESC + "[" + row + AnsiConstants.SEPARATOR + col + AnsiConstants.CURSOR_SUFFIX);
        });
    }

    /**
     * 光标向上移动N行（保留当前列位置）
     *
     * @param n 移动行数（正整数）
     */
    public static String moveCursorUp(int n) {
        return AnsiConstants.ESC + "[" + Math.max(1, n) + "A";
    }

    /**
     * 光标向下移动N行（保留当前列位置）
     *
     * @param n 移动行数（正整数）
     */
    public static String moveCursorDown(int n) {
        return AnsiConstants.ESC + "[" + Math.max(1, n) + "B";
    }

    /**
     * 光标向右移动N列（保留当前行位置）
     *
     * @param n 移动列数（正整数）
     */
    public static String moveCursorRight(int n) {
        return AnsiConstants.ESC + "[" + Math.max(1, n) + "C";
    }

    // ================================== 清除动态操作 ==================================

    /**
     * 光标向左移动N列（保留当前行位置）
     *
     * @param n 移动列数（正整数）
     */
    public static String moveCursorLeft(int n) {
        return AnsiConstants.ESC + "[" + Math.max(1, n) + "D";
    }

    /**
     * 清除指定行的内容
     *
     * @param row 要清除的行（终端坐标，从1开始）
     */
    public static void clearLine(Terminal terminal, int row) {
        executeTerminalOperation(() -> {
            moveCursor(row, 1);
            terminal.writer().write(AnsiConstants.CLEAR_LINE);
        });
    }

    // ================================== 样式动态操作 ==================================

    /**
     * 清除指定范围的内容
     *
     * @param startRow 起始行
     * @param endRow   结束行
     */
    public static void clearLines(Terminal terminal, int startRow, int endRow) {
        if (startRow > endRow) return;
        executeTerminalOperation(() -> {
            for (int row = startRow; row <= endRow; row++) {
                moveCursor(row, 1);
                terminal.writer().write(AnsiConstants.CLEAR_LINE);
            }
        });
    }

    /**
     * 拼接自定义样式（支持多个样式组合）
     *
     * @param styles 可变参数，传入AnsiConstants中的样式常量
     * @return 组合后的ANSI序列
     */
    public static String combineStyles(String... styles) {
        if (styles == null || styles.length == 0) return AnsiConstants.STYLE_RESET;
        StringBuilder sb = new StringBuilder();
        for (String style : styles) {
            if (style != null && !style.isEmpty()) {
                sb.append(style);
            }
        }
        return sb.toString();
    }

    // ================================== 终端原子操作（线程安全） ==================================

    /**
     * 快速设置“文字颜色+背景色+加粗”
     *
     * @param fgColor 前景色
     * @param bgColor 背景色
     * @return 组合后的ANSI序列
     */
    public static String setStyle(String fgColor, String bgColor) {
        return combineStyles(AnsiConstants.STYLE_BOLD, fgColor, bgColor);
    }

    /**
     * 原子执行终端操作（自动加锁，确保线程安全，执行后自动flush）
     *
     * @param operation 终端操作逻辑（Lambda表达式）
     */
    public static void executeTerminalOperation(Runnable operation) {
        synchronized (terminal) { // 全局终端锁，避免多线程并发冲突
            try {
                operation.run();
            } finally {
                terminal.flush(); // 强制刷新，确保操作即时生效
            }
        }
    }
}
