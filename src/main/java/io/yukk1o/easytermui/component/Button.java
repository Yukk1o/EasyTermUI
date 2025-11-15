package io.yukk1o.easytermui.component;

import io.yukk1o.easytermui.base.BaseComponent;
import io.yukk1o.easytermui.util.AnsiUtils;
import lombok.Getter;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;

import java.util.function.Consumer;

import static io.yukk1o.easytermui.EasyTermUI.terminal;

public class Button extends BaseComponent {
    /**
     * -- GETTER --
     *  获取按钮文本
     */
    @Getter
    private final String text;
    /// 触发动作
    private final Runnable action;
    private final Consumer<Button> consumerAction;

//    /**
//     * 按钮构造函数
//     *
//     * @param relX   相对坐标
//     * @param relY   相对坐标
//     * @param text   按钮文本
//     * @param action 按钮点击动作
//     */
//    public Button(int relX, int relY, String text, Runnable action) {
//        super(relX, relY, text.length() + 4, 1);
//        this.text = text;
//        this.action = action;
//        this.consumerAction = null;
//    }
//
//    /**
//     * 按钮构造函数
//     *
//     * @param relX   相对坐标
//     * @param relY   相对坐标
//     * @param width  宽度
//     * @param text   按钮文本
//     * @param action 按钮点击动作
//     */
//    public Button(int relX, int relY, int width, String text, Runnable action) {
//        super(relX, relY, width, 1);
//        if (width < text.length() + 4) {
//            throw new IllegalArgumentException();
//        }
//        this.text = text;
//        this.action = action;
//        this.consumerAction = null;
//    }

    /**
     * 按钮构造函数
     *
     * @param relX   相对坐标
     * @param relY   相对坐标
     * @param text   按钮文本
     * @param consumerAction 按钮点击动作
     */
    public Button(int relX, int relY, String text, Consumer<Button> consumerAction) {
        super(relX, relY, text.length() + 4, 1);
        this.text = text;
        this.action = null;
        this.consumerAction = consumerAction;
    }

    /**
     * 按钮构造函数
     *
     * @param relX   相对坐标
     * @param relY   相对坐标
     * @param width  宽度
     * @param text   按钮文本
     * @param consumerAction 按钮点击动作
     */
    public Button(int relX, int relY, int width, String text, Consumer<Button> consumerAction) {
        super(relX, relY, width, 1);
        if (width < text.length() + 4) {
            throw new IllegalArgumentException();
        }
        this.text = text;
        this.action = null;
        this.consumerAction = consumerAction;
    }

    /**
     * 绘制按钮
     */
    @Override
    public void renderContext() {
        AnsiUtils.cursorHide();
        AnsiUtils.moveCursor(this.absY, this.absX);

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
        if (action != null) {
            action.run();
        } else if (consumerAction != null) {
            consumerAction.accept(this);
        }
    }

}
