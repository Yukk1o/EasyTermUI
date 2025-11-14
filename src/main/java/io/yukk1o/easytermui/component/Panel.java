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

///     private final int relX; // 相对X（父Panel时）/ 绝对X（顶层时）
//    private final int relY; // 相对Y（父Panel时）/ 绝对Y（顶层时）
//    /// 绝对坐标
//    private volatile int absX;
//    private volatile int absY;
//    // 面板尺寸
//    private final int width;      // 面板宽度（固定）
//    private final int height;     // 面板高度（固定）
//    private final List<Component> children = new ArrayList<>(); // 子组件列表
//    private final List<Panel> childrenPanel = new ArrayList<>(); /// 子面板列表
//
//    // ================================== 构造方法 ==================================
//
//    /**
//     * 构造「嵌套Panel」（相对坐标）
//     *
//     * @param relX   相对于父Panel的X坐标
//     * @param relY   相对于父Panel的Y坐标
//     * @param width  面板宽度
//     * @param height 面板高度
//     */
//    public Panel(int relX, int relY, int width, int height) {
//        this.relX = relX;
//        this.relY = relY;
//        this.width = width;
//        this.height = height;
//    }
//
//    // ================================= 子组件管理 =================================
//
//    /**
//     * 添加子组件
//     */
//    public void addComponent(Component component) {
//        boolean isOutOfWidth = component.getRelX() + component.getWidth() > this.width;
//
//        boolean isOutOfHeight = component.getRelY() + component.getHeight() > this.height;
//        if (isOutOfHeight || isOutOfWidth) {
//            throw new IllegalArgumentException("子组件超出父面板边界！父面板：宽" + width + "×高" + height);
//        }
//        if (component instanceof Panel) {
//            childrenPanel.add((Panel) component);
//            return;
//        }
//        children.add(component);
//    }
//
//    /**
//     * 移除子组件
//     */
//    public void removeComponent(Component component) {
//        children.remove(component);
//    }
//
//    // ================================== Component接口实现 ==================================
//
//    /**
//     * 子面板渲染
//     */
//    @Override
//    public void render(int offsetX, int offsetY) {
//        AnsiUtils.executeTerminalOperation(() -> {
//            try {
//                /// 1. 计算当前Panel的绝对坐标
//                this.absX = offsetX + this.relX;
//                this.absY = offsetY + this.relY;
//
//                /// 2. 递归渲染所有子组件
//                for (Component component : children) {
//                    component.render(this.absX, this.absY);
//                }
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });
//    }
//
//    /**
//     * 点击事件分发（核心：递归转换坐标+传递事件）
//     */
//    @Override
//    public boolean onClick(int clickAbsX, int clickAbsY) {
//        /// 1. 判断点击事件是否在当前Panel内
//        if (!isInside(clickAbsX, clickAbsY)) {
//            return false;
//        }
//
//        /// 3.递归分发点击事件给子组件(子组件优先处理, 子面板优先处理)
//        for (Panel childPanel : childrenPanel) {
//            if (childPanel.onClick(clickAbsX, clickAbsY)) {
//                return true;
//            }
//        }
//
//        for (Component component : children) {
//            if (component.onClick(clickAbsX, clickAbsY)) {
//                return true;
//            }
//        }
//
//        handleBlankClick();
//        return true;
//    }
//
//    @Override
//    public boolean isInside(int clickAbsX, int clickAbsY) {
//        return clickAbsX >= this.absX
//                && clickAbsX < this.absX + this.width
//                && clickAbsY >= this.absY
//                && clickAbsY < this.absY + this.height;
//    }
//
//    /**
//     * 点击Panel空白区域的处理逻辑（如取消输入框焦点）
//     */
//     private void handleBlankClick() {
//         if (activeInputBox != null) {
//             activeInputBox.setEditing(false);
//         }
//     }
//
//    // ================================== Getter方法 ==================================
//    @Override
//    public int getWidth() {
//        return width;
//    }
//
//    @Override
//    public int getHeight() {
//        return height;
//    }
//
//    @Override
//    public int getRelX() {
//        return relX;
//    }
//
//    @Override
//    public int getRelY() {
//        return relY;
//    }
//
//    @Override
//    public int getAbsX() {
//        return 0;
//    }
//
//    @Override
//    public int getAbsY() {
//        return 0;
//    }
//
//    public List<Component> getChildren() {
//        return new ArrayList<>(children); // 返回不可修改列表，避免外部篡改
//    }
