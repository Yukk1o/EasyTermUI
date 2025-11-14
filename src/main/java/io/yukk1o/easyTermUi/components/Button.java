package io.yukk1o.easyTermUi.components;

import io.yukk1o.easyTermUi.base.BaseComponent;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;

import static io.yukk1o.easyTermUi.EasyTermUI.terminal;

public class Button extends BaseComponent {
    private final String text;
    /// 触发动作
    private final Runnable action;

    /**
     * 按钮构造函数
     *
     * @param relX   相对坐标
     * @param relY   相对坐标
     * @param text   按钮文本
     * @param action 按钮点击动作
     */
    public Button(int relX, int relY, String text, Runnable action) {
        super(relX, relY, text.length() + 4, 1);
        this.text = text;
        this.action = action;
    }

    /**
     * 按钮构造函数
     *
     * @param relX   相对坐标
     * @param relY   相对坐标
     * @param width  宽度
     * @param text   按钮文本
     * @param action 按钮点击动作
     */
    public Button(int relX, int relY, int width, String text, Runnable action) {
        super(relX, relY, width, 1);
        if (width < text.length() + 4) {
            throw new IllegalArgumentException();
        }
        this.text = text;
        this.action = action;
    }

    /**
     * 绘制按钮
     */
    @Override
    public void renderContext() {
        /// 设置按钮样式(此处为蓝色 + 变粗)
        AttributedString buttonText = new AttributedString(
                "[ " + text + " ]",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE).bold());

        /// 绘制按钮
        buttonText.print(terminal);
    }

    /**
     * 处理点击事件
     *
     * @param clickAbsX 绝对X坐标（点击位置）
     * @param clickAbsY 绝对Y坐标（点击位置）
     */
    @Override
    public void handleClick(int clickAbsX, int clickAbsY) {
        action.run();
    }

    /**
     * 获取按钮文本
     */
    public String getText() {
        return text;
    }
}
