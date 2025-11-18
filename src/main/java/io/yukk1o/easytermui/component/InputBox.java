package io.yukk1o.easytermui.component;

import io.yukk1o.easytermui.base.BaseComponent;
import io.yukk1o.easytermui.constant.AnsiConstants;
import io.yukk1o.easytermui.util.AnsiUtils;

import static io.yukk1o.easytermui.EasyTermUI.terminal;
import static io.yukk1o.easytermui.EasyTermUI.activeInputBox;

public class InputBox extends BaseComponent {
    /// 输入相关属性
    private final int maxLength;
    /// 最大输入长度
    private final StringBuilder inputValue = new StringBuilder();
    /// 输入值
    private final String escape;
    private volatile boolean isEditing = false;
    /// 编辑状态
    private int cursorPos = 0; /// 光标位置

    /**
     * 构造函数
     * 需要预留两行的位置
     *
     * @param relX      起始列
     * @param relY      起始行
     * @param maxLength 最大输入长度
     */
    public InputBox(int relX, int relY, int maxLength) {
        super(relX, relY, maxLength, 2);
        this.maxLength = maxLength;

        this.escape = "\b".repeat(maxLength);
    }

    // ================================== Component接口实现 ==================================

    @Override
    public void render(int offsetX, int offsetY) {
        /// 记录绝对坐标
        this.absX = offsetX + this.relX;
        this.absY = offsetY + this.relY;
        AnsiUtils.executeTerminalOperation(() -> {
            AnsiUtils.cursorHide();
            AnsiUtils.moveCursor(this.absY + 1, this.absX);
            terminal.writer().write(AnsiConstants.STYLE_BOLD_BLUE + "‾".repeat(maxLength) + AnsiConstants.STYLE_RESET);
        });
        render();
    }


    /**
     * 绘制输入框
     */
    @Override
    public void renderContext() {
        /// 1. 绘制输入内容
        AnsiUtils.cursorHide();
        AnsiUtils.moveCursor(this.absY, this.absX + maxLength);

        String displayText = String.format("%-" + maxLength + "s", inputValue);
        terminal.writer().print(escape + displayText);

        /// 2. 控制光标
        if (isEditing) {
            AnsiUtils.moveCursor(this.absY, this.absX + cursorPos);
            AnsiUtils.cursorShow();
        } else {
            AnsiUtils.cursorHide();
        }
    }

    /**
     * 点击事件处理
     */
    @Override
    public void handleClick(int clickAbsX, int clickAbsY) {
        /// 激活编辑状态，定位光标
        cursorPos = Math.min(clickAbsX - this.absX, inputValue.length());
        activeInputBox = this;
        this.isEditing = true;

        synchronized (terminal) {
            AnsiUtils.moveCursor(this.absY, this.absX + cursorPos);
            AnsiUtils.cursorShow();
        }
    }

    // ================================== 输入/光标控制方法 ==================================


    /**
     * 接收键盘输入（字母/数字/退格）
     * 仅在编辑状态（isEditing=true）时处理输入
     *
     * @param charCode 字符ASCII码
     */
    public void insert(int charCode) {
        if (!isEditing) return;

        char inputChar = (char) charCode;

        AnsiUtils.executeTerminalOperation(() -> {
            /// 处理删除
            if ((charCode == 127 || charCode == 8) && cursorPos > 0) {
                inputValue.deleteCharAt(cursorPos - 1);
                cursorPos--;
            }

            /// 处理普通字符
            else if (inputValue.length() < maxLength
                    && Character.isLetterOrDigit(inputChar)
                    && !Character.isISOControl(inputChar)) {
                inputValue.insert(cursorPos, inputChar);
                cursorPos++;
            }

            render();
        });
    }

    /**
     * 方向键移动光标
     */
    public void moveCursor(int arrowKey) {
        if (!isEditing) return; // 非编辑状态，忽略

        AnsiUtils.executeTerminalOperation(() -> {
            switch (arrowKey) {
                case 'C' -> cursorPos = Math.min(inputValue.length(), cursorPos + 1);

                case 'D' -> cursorPos = Math.max(0, cursorPos - 1);

                default -> {
                    return;
                }
            }

            AnsiUtils.moveCursor(this.absY, this.absX + cursorPos);
        });
    }

// ------------------- Getter / Setter --------------------

    /**
     * 获取输入值
     */
    public String getValue() {
        return inputValue.toString().trim().isEmpty() ? null : inputValue.toString().trim();
    }

    /**
     * 获取输入框编辑状态
     */
    public boolean isEditing() {
        return isEditing;
    }

    /**
     * 设置输入框编辑状态
     */
    public void setEditing(boolean editing) {
        isEditing = editing;
        AnsiUtils.cursorHide();
    }
}
