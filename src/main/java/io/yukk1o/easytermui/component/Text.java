package io.yukk1o.easytermui.component;

import io.yukk1o.easytermui.base.BaseComponent;
import io.yukk1o.easytermui.util.AnsiUtils;
import io.yukk1o.easytermui.util.PrintUtils;

public class Text extends BaseComponent {
    String text;


    /**
     * 文本构建函数
     *
     * @param relX 相对坐标
     * @param relY 相对坐标
     * @param width 宽度
     * @param height 高度
     * @param text  文本内容
     */
    public Text(int relX, int relY, int width, int height, String text) {
        super(relX, relY, width, height);
        this.text = text;
    }

    @Override
    public void renderContext() {
        AnsiUtils.cursorHide();
        if (text == null || text.isEmpty()) {
            return;
        }

        int totalLines = Math.min(height, (text.length() + width - 1) / width);

        for (int i = 0; i < totalLines; i++) {
            int start = i * width;
            int end = Math.min((i + 1) * width, text.length());

            String line = text.substring(start, end);
            line = String.format("%-" + width + "s", line);

            PrintUtils.printAt(absY + i, absX, line);
        }
    }

    @Override
    public void handleClick(int clickAbsX, int clickAbsY) {
        return;
    }

    /**
     * 设置文本内容
     *
     * @param text 文本内容
     */
    public void setText(String text) {
        this.text = text;
    }
}
