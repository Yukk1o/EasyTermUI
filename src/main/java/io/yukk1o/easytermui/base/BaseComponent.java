package io.yukk1o.easytermui.base;

import io.yukk1o.easytermui.util.AnsiUtils;

public abstract class BaseComponent implements Component {
    /// 相对X
    protected int relX;
    /// 相对Y
    protected int relY;
    /// 宽度
    protected final int width;
    /// 高度
    protected final int height;
    /// 绝对Y
    protected volatile int absX;
    /// 绝对X
    protected volatile int absY;

    /// 绑定数据
    protected Object bindData;

    /// ================================= 自带方法 ==================================
    /**
     * 组件构造函数
     *
     * @param relX   相对坐标
     * @param relY   相对坐标
     * @param width  组件宽度
     * @param height 组件高度
     */
    public BaseComponent(int relX, int relY, int width, int height) {
        this.relX = relX;
        this.relY = relY;
        this.width = width;
        this.height = height;

        if (relX < 0 || relY < 0) {
            throw new IllegalArgumentException(
                    String.format("组件坐标不能为负数！组件坐标：(%d, %d)", relX, relY)
            );
        }

        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException(
                    String.format("组件宽高不能小于等于0！组件宽高：(%d, %d)", width, height)
            );
        }
    }

    /**
     * 组件构造函数
     * 调用该构建函数时，记得要自己设置长/宽
     * 默认使用1*1的大小
     *
     * @param relX 相对坐标
     * @param relY 相对坐标
     */
    public BaseComponent(int relX, int relY) {
        this.relX = relX;
        this.relY = relY;
        this.width = 1;
        this.height = 1;
    }


    /**
     * 带偏移量的渲染（核心方法）
     * 仅调用一次，用于初始化组件
     * 如若需要自定义逻辑,请重写render方法
     *
     * @param offsetX 父组件的绝对X坐标（偏移量）
     * @param offsetY 父组件的绝对Y坐标（偏移量）
     */
    @Override
    public void render(int offsetX, int offsetY) {
        this.absX = offsetX + this.relX;
        this.absY = offsetY + this.relY;
        render();
    }

    /**
     * 绘制组件
     */
    public void render() {
        AnsiUtils.executeTerminalOperation(this::renderContext);
    }

    /**
     * 处理点击事件（核心方法）
     *
     * @param clickAbsX 绝对X坐标（点击位置）
     * @param clickAbsY 绝对Y坐标（点击位置）
     * @return 是否消费该事件（true=事件已处理，false=继续传递）
     */
    @Override
    public boolean onClick(int clickAbsX, int clickAbsY) {
        if (isInside(clickAbsX, clickAbsY)) {
            handleClick(clickAbsX, clickAbsY);
            return true;
        }
        return false;
    }

    /**
     * 判断「绝对位置」是否在组件范围内（核心校验逻辑）
     *
     * @param X 绝对X
     * @param Y 绝对Y
     */
    @Override
    public boolean isInside(int X, int Y) {
        return X >= absX && X < absX + this.width && Y >= absY && Y < absY + height;
    }

    /// ================================= 开发方法 ==================================

    /**
     * 内容渲染(需开发者手动实现)
     * 记得自己移动光标 ! ! !
     */
    public abstract void renderContext();

    /**
     * 点击处理(需开发者手动实现)
     */
    public abstract void handleClick(int clickAbsX, int clickAbsY);


    /// =========================== Getter方法 / Setter方法 ===========================

    /**
     * 获取相对X
     */
    @Override
    public int getRelX() {
        return relX;
    }

    /**
     * 获取相对Y
     */
    @Override
    public int getRelY() {
        return relY;
    }

    /**
     * 获取组件宽度
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * 获取组件高度
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * 获取绝对X
     */
    @Override
    public int getAbsX() {
        return absX;
    }

    /**
     * 获取绝对Y
     */
    @Override
    public int getAbsY() {
        return absY;
    }

    /**
     * 设置相对X
     */
    @Override
    public void setRelX(int relX) {
        this.relX = relX;
    }

    /**
     * 设置相对Y
     */
    @Override
    public void setRelY(int relY) {
        this.relY = relY;
    }

    /**
     * 获取绑定数据
     */
    @Override
    public Object getBindData() {
        return bindData;
    }

    /**
     * 设置绑定数据
     */
    @Override
    public void setBindData(Object bindData) {
        this.bindData = bindData;
    }

    /**
     * 组件信息
     */
    @Override
    public String toString() {
        return "BaseComponent{" +
                "relX=" + relX +
                ", relY=" + relY +
                ", width=" + width +
                ", height=" + height +
                ", absX=" + absX +
                ", absY=" + absY +
                '}';
    }
}
