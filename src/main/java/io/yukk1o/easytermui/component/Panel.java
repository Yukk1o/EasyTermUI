package io.yukk1o.easytermui.component;

import io.yukk1o.easytermui.base.BasePanel;

public class Panel extends BasePanel {


    /**
     * 构造「嵌套Panel」（相对坐标）
     *
     * @param relX   相对于父Panel的X坐标
     * @param relY   相对于父Panel的Y坐标
     * @param width  面板宽度
     * @param height 面板高度
     */
    public Panel(int relX, int relY, int width, int height) {
        super(relX, relY, width, height);
    }
}

