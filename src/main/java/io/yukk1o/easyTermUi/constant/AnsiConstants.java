package io.yukk1o.easyTermUi.constant;

/**
 * ANSI控制序列常量类
 * 兼容VT100/ANSI标准终端，按功能分类，便于查找和维护
 * 参考标准：<a href="https://en.wikipedia.org/wiki/ANSI_escape_code">...</a>
 */
public final class AnsiConstants {
    // ================================== 基础核心常量（不可修改）==================================
    /**
     * ANSI转义字符（所有序列的前缀）
     */
    public static final String ESC = "\033";
    /**
     * 序列分隔符（用于多参数拼接，如颜色+样式）
     */
    public static final String SEPARATOR = ";";
    /**
     * 光标移动/定位序列后缀
     */
    public static final String CURSOR_SUFFIX = "H";
    /**
     * 颜色/样式序列后缀
     */
    public static final String STYLE_SUFFIX = "m";
    /**
     * 清除序列后缀
     */
    public static final String CLEAR_SUFFIX = "J";
    /**
     * 行清除序列后缀
     */
    public static final String LINE_CLEAR_SUFFIX = "K";

    // ================================== 光标控制常量 ==================================
    /**
     * 显示光标（默认状态）
     */
    public static final String CURSOR_SHOW = ESC + "[?25h";
    /**
     * 隐藏光标（UI组件编辑状态切换时常用）
     */
    public static final String CURSOR_HIDE = ESC + "[?25l";
    /**
     * 光标归位（终端左上角：行1，列1）
     */
    public static final String CURSOR_RESET = ESC + "[1" + SEPARATOR + "1" + CURSOR_SUFFIX;
    /**
     * 清除整个屏幕（光标归位到左上角）
     */
    public static final String CLEAR_SCREEN = ESC + "[2" + CLEAR_SUFFIX + CURSOR_RESET;
    /**
     * 清除屏幕所有内容+缓冲区（彻底清空）
     */
    public static final String CLEAR_SCREEN_FULL = ESC + "[3" + CLEAR_SUFFIX + CURSOR_RESET;
    /**
     * 光标向上移动1行（保留列位置）
     */
    public static final String CURSOR_UP = ESC + "[1A";
    /**
     * 光标向下移动1行（保留列位置）
     */
    public static final String CURSOR_DOWN = ESC + "[1B";

    // ================================== 清除控制常量 ==================================
    /**
     * 光标向右移动1列（保留行位置）
     */
    public static final String CURSOR_RIGHT = ESC + "[1C";
    /**
     * 光标向左移动1列（保留行位置）
     */
    public static final String CURSOR_LEFT = ESC + "[1D";
    /**
     * 清除从光标到行尾的内容（不影响光标位置）
     */
    public static final String CLEAR_LINE_FROM_CURSOR = ESC + "[0" + LINE_CLEAR_SUFFIX;
    /**
     * 清除整行内容（光标位置不变）
     */
    public static final String CLEAR_LINE = ESC + "[2" + LINE_CLEAR_SUFFIX;
    /**
     * 清除从光标到屏幕底部的内容
     */
    public static final String CLEAR_SCREEN_FROM_CURSOR = ESC + "[0" + CLEAR_SUFFIX;

    // ================================== 显示样式常量 ==================================
    /**
     * 重置所有显示属性（颜色、加粗、下划线等恢复默认）
     */
    public static final String STYLE_RESET = ESC + "[0" + STYLE_SUFFIX;
    /**
     * 加粗样式（常用）
     */
    public static final String STYLE_BOLD = ESC + "[1" + STYLE_SUFFIX;
    /**
     * 弱化样式（灰色显示）
     */
    public static final String STYLE_FAINT = ESC + "[2" + STYLE_SUFFIX;
    /**
     * 斜体样式（部分终端不支持）
     */
    public static final String STYLE_ITALIC = ESC + "[3" + STYLE_SUFFIX;
    /**
     * 单下划线样式（常用）
     */
    public static final String STYLE_UNDERLINE_SINGLE = ESC + "[4" + STYLE_SUFFIX;
    /**
     * 双下划线样式（部分终端不支持）
     */
    public static final String STYLE_UNDERLINE_DOUBLE = ESC + "[21" + STYLE_SUFFIX;
    /**
     * 反显样式（前景色与背景色互换，常用）
     */
    public static final String STYLE_INVERT = ESC + "[7" + STYLE_SUFFIX;
    /**
     * 隐藏样式（文字透明，部分终端不支持）
     */
    public static final String STYLE_HIDE = ESC + "[8" + STYLE_SUFFIX;

    // ================================== 前景色（文字颜色）常量 ==================================
    public static final String FG_BLACK = ESC + "[30" + STYLE_SUFFIX;
    public static final String FG_RED = ESC + "[31" + STYLE_SUFFIX;
    /**
     * 红色加粗（警告/退出按钮常用）
     */
    public static final String STYLE_BOLD_RED = STYLE_BOLD + FG_RED;
    public static final String FG_GREEN = ESC + "[32" + STYLE_SUFFIX;
    /**
     * 绿色加粗（成功按钮常用）
     */
    public static final String STYLE_BOLD_GREEN = STYLE_BOLD + FG_GREEN;
    public static final String FG_YELLOW = ESC + "[33" + STYLE_SUFFIX;
    /**
     * 黄色加粗（提示文字常用）
     */
    public static final String STYLE_BOLD_YELLOW = STYLE_BOLD + FG_YELLOW;
    public static final String FG_BLUE = ESC + "[34" + STYLE_SUFFIX;
    /**
     * 蓝色加粗（输入框下划线常用）
     */
    public static final String STYLE_BOLD_BLUE = STYLE_BOLD + FG_BLUE;
    public static final String FG_MAGENTA = ESC + "[35" + STYLE_SUFFIX;
    public static final String FG_CYAN = ESC + "[36" + STYLE_SUFFIX;
    public static final String FG_WHITE = ESC + "[37" + STYLE_SUFFIX;
    /**
     * 前景色默认（恢复终端默认文字颜色）
     */
    public static final String FG_DEFAULT = ESC + "[39" + STYLE_SUFFIX;
    // ================================== 背景色（文字背景）常量 ==================================
    public static final String BG_BLACK = ESC + "[40" + STYLE_SUFFIX;
    public static final String BG_RED = ESC + "[41" + STYLE_SUFFIX;
    public static final String BG_GREEN = ESC + "[42" + STYLE_SUFFIX;
    public static final String BG_YELLOW = ESC + "[43" + STYLE_SUFFIX;
    public static final String BG_BLUE = ESC + "[44" + STYLE_SUFFIX;

    // ================================== 常用组合样式（高频复用） ==================================
    public static final String BG_MAGENTA = ESC + "[45" + STYLE_SUFFIX;
    public static final String BG_CYAN = ESC + "[46" + STYLE_SUFFIX;
    public static final String BG_WHITE = ESC + "[47" + STYLE_SUFFIX;
    /**
     * 背景色默认（恢复终端默认背景颜色）
     */
    public static final String BG_DEFAULT = ESC + "[49" + STYLE_SUFFIX;

    // 私有构造器：禁止实例化（纯常量类）
    private AnsiConstants() {
    }
}
