package io.yukk1o.easyTermUi.base;

import io.yukk1o.easyTermUi.ulitis.AnsiUtils;

import java.util.ArrayList;
import java.util.List;

import static io.yukk1o.easyTermUi.EasyTermUI.activeInputBox;

public abstract class BasePanel extends BaseComponent {
    protected final List<BaseComponent> children = new ArrayList<>(); // 子组件列表
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
        boolean isOutOfWidth = component.getRelX() + component.getWidth() > this.width;

        boolean isOutOfHeight = component.getRelY() + component.getHeight() > this.height;
        if (isOutOfHeight || isOutOfWidth) {
            throw new IllegalArgumentException("子组件超出父面板边界！父面板：宽" + width + "×高" + height);
        }
        children.add(component);
        component.parent = this;
    }

    /**
     * 移除子组件
     */
    public void removeComponent(BaseComponent component) {
        children.remove(component);
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
//            System.out.println("面板位置"  + " (" + absX + ", " + absY + " )");
//            System.out.println("面板属性" + " (" + width + ", " + height + " )");
//            System.out.println("点击位置" + " (" + clickAbsX + ", " + clickAbsY + " )");
//            System.out.println(isInside(clickAbsX, clickAbsY));
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
    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getRelX() {
        return relX;
    }

    @Override
    public int getRelY() {
        return relY;
    }

    @Override
    public int getAbsX() {
        return 0;
    }

    @Override
    public int getAbsY() {
        return 0;
    }

    public List<Component> getChildren() {
        return new ArrayList<>(children); // 返回不可修改列表，避免外部篡改
    }
}
