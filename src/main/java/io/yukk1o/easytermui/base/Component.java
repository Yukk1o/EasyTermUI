package io.yukk1o.easytermui.base;

/**
 * 所有UI组件的统一接口（支持相对坐标+偏移量模式）
 */
public interface Component {
    // ================================== 渲染相关 ==================================

    /**
     * 顶层渲染（仅顶层面板调用，偏移量为自身绝对坐标）
     */
    default void render() {
        render(0, 0);
    }

    /**
     * 带偏移量的渲染（核心方法）
     *
     * @param offsetX 父组件的绝对X坐标（偏移量）
     * @param offsetY 父组件的绝对Y坐标（偏移量）
     */
    void render(int offsetX, int offsetY);

    // ================================== 事件相关 ==================================

    /**
     * 处理点击事件（核心方法）
     *
     * @param clickAbsX 绝对X坐标（点击位置）
     * @param clickAbsY 绝对Y坐标（点击位置）
     * @return 是否消费该事件（true=事件已处理，false=继续传递）
     */
    boolean onClick(int clickAbsX, int clickAbsY);

    // ================================== 尺寸/边界相关 ==================================

    /**
     * 判断「相对于自身的坐标」是否在组件范围内（核心校验逻辑）
     *
     * @param X 绝对X
     * @param Y 绝对Y
     */
    boolean isInside(int X, int Y);

    /**
     * 获取组件宽度
     */
    int getWidth();

    /**
     * 获取组件高度
     */
    int getHeight();

    /**
     * 获取组件相对X坐标
     */
    int getRelX();

    /**
     * 获取组件相对Y坐标
     */
    int getRelY();

    /**
     * 获取组件绝对X坐标
     */
    int getAbsX();

    /**
     * 获取组件绝对Y坐标
     */
    int getAbsY();

    /**
     * 设置组件相对X坐标
     */
    void setRelX(int relX);

    /**
     * 设置组件相对Y坐标
     */
    void setRelY(int relY);
}