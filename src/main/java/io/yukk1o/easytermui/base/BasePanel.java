package io.yukk1o.easytermui.base;

import io.yukk1o.easytermui.util.AnsiUtils;

import java.util.ArrayList;
import java.util.List;

import static io.yukk1o.easytermui.EasyTermUI.activeInputBox;

public abstract class BasePanel extends BaseComponent {
    private List<BaseComponent> children = new ArrayList<>(); // 子组件列表
    // ================================== 构造方法 ==================================

    /**
     * 构造「嵌套Panel」（相对坐标）
     *
     * @param relX   相对于父Panel的X坐标
     * @param relY   相对于父Panel的Y坐标
     * @param width  面板宽度
     * @param height 面板高度
     */
    public BasePanel(int relX, int relY, int width, int height) {
        super(relX, relY, width, height);
    }

    // ================================= 子组件管理 =================================

    /**
     * 添加子组件
     */
    public void addComponent(BaseComponent component) {
        /// 1. 校验子组件非空
        if (component == null) {
            throw new IllegalArgumentException("子组件不能为null");
        }

        /// 3. 校验子组件是否超出父面板边界
        boolean isOutOfWidth = component.getRelX() + component.getWidth() > this.width;

        boolean isOutOfHeight = component.getRelY() + component.getHeight() > this.height;

        if (isOutOfHeight || isOutOfWidth) {
            throw new IllegalArgumentException("子组件超出父面板边界！父面板：宽" + width + "×高" + height + "组件ID: " + component);
        }

        children.add(component);
    }

    /**
     * 移除子组件
     */
    public void removeComponent(BaseComponent component) {
        children.remove(component);
    }

    /**
     * 移除所有子组件
     */
    public void removeAllComponents() {
        children.clear();
    }

    // ================================== 板块处理 ==================================

    /**
     * 清空面板
     * 组件需要重新渲染,否则无法正常使用
     */
    public void clear() {
        AnsiUtils.cursorHide();
        AnsiUtils.clear(this.absY, this.absY + this.height, this.absX, this.absX + this.width);
    }

    // ================================== Component接口实现 ==================================

    /**
     * 子面板渲染
     */
    @Override
    public void renderContext() {
        AnsiUtils.executeTerminalOperation(() -> {
            try {
                /// 递归渲染所有子组件
                for (Component component : children) {
                    component.render(this.absX, this.absY);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void handleClick(int clickAbsX, int clickAbsY) {
        /// 1. 判断点击事件是否在当前Panel内
        if (isInside(clickAbsX, clickAbsY)) {
            for (BaseComponent component : children) {
                if (component.onClick(clickAbsX, clickAbsY)) {
                    return;
                }
            }
        }


        /// 3. 点击空位置处理
        handleBlankClick();
    }

    /**
     * 点击事件分发（核心：递归转换坐标+传递事件）
     */
    @Override
    public boolean onClick(int clickAbsX, int clickAbsY) {
        /// 1. 判断点击事件是否在当前Panel内
        if (!isInside(clickAbsX, clickAbsY)) {
            return false;
        }

        /// 2. 下发给子组件
        for (BaseComponent component : children) {
            if (component.onClick(clickAbsX, clickAbsY)) {
                return true;
            }
        }

        /// 3. 点击空位置处理
        handleBlankClick();
        return true;
    }

    /**
     * 点击Panel空白区域的处理逻辑（如取消输入框焦点）
     */
    private void handleBlankClick() {
        if (activeInputBox != null) {
            activeInputBox.setEditing(false);
        }
    }

    // ================================== Getter方法 ==================================

    public List<Component> getChildren() {
        return new ArrayList<>(children);
    }
}
