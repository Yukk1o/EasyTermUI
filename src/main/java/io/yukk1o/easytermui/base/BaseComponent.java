package io.yukk1o.easytermui.base;

import io.yukk1o.easytermui.util.AnsiUtils;

public abstract class BaseComponent implements Component {
    /// 坐标
    protected final int relX;
    /// 相对X
    protected final int relY;
    /// 绝对Y
    /// 尺寸
    protected final int width;
    /// 宽度
    protected final int height;
    /// 相对Y
    /// 绝对坐标
    protected volatile int absX;
    /// 绝对X
    protected volatile int absY;
    /// 高度
    /// 父组件
    BasePanel parent;

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

    //================================== 尺寸/边界相关 ==================================

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
     * 获取组件相对X坐标
     */
    @Override
    public int getRelX() {
        return relX;
    }

    /**
     * 获取组件相对Y坐标
     */
    @Override
    public int getRelY() {
        return relY;
    }

    /**
     * 获取组件绝对X坐标
     */
    public int getAbsX() {
        return absX;
    }

    /**
     * 获取组件绝对Y坐标
     */
    public int getAbsY() {
        return absY;
    }
    //================================== 尺寸/边界相关 ==================================
}
