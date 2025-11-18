package io.yukk1o.easytermui;

import io.yukk1o.easytermui.base.BasePanel;
import io.yukk1o.easytermui.component.InputBox;
import io.yukk1o.easytermui.component.Panel;
import io.yukk1o.easytermui.base.Listener.InteractiveListener;
import io.yukk1o.easytermui.constant.AnsiConstants;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.io.PrintWriter;

public class EasyTermUI {
    /// 终端
    public static final Terminal terminal;
    /// 初始化
    public Boolean initialized = false;
    /// 激活组件
    public static InputBox activeInputBox;
    /// 终端宽度
    /// 输出器
    public static PrintWriter writer;
    /// 监听器
    InteractiveListener interactiveListener;

    static {
        try {
            terminal = TerminalBuilder.builder()
                    .system(true)
                    .provider("jni")
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        terminal.trackMouse(Terminal.MouseTracking.Normal);
        terminal.flush();
        writer = terminal.writer();
        /// 清屏
        terminal.puts(InfoCmp.Capability.clear_screen);
        writer.flush();
    }

    /// 根面板
    public BasePanel rootPanel;
    int terminalHeight = terminal.getHeight();
    /// 终端高度
    int terminalWidth = terminal.getWidth();
    /// Debug模式开关
    private Boolean debug = false;

    /**
     * 构建方法
     */
    public EasyTermUI init(BasePanel rootPanel) {
        if (initialized) {
            return this;
        }

        /// 初始化终端大小
        int height = terminal.getHeight();
        int width = terminal.getWidth();
        /// 检测终端大小是否符合面板要求
        if (height < rootPanel.getHeight() || width < rootPanel.getWidth()) {
            String errorMessage = String.format("您的终端大小不足够\n请设置终端大小为 %d * %d\n", rootPanel.getWidth(), rootPanel.getHeight());
            System.out.println(errorMessage);
            /// TODO: 将来添加弹窗
        }

        this.rootPanel = rootPanel;
        initialized = true;


        interactiveListener = new InteractiveListener(rootPanel);
        start();

        return this;
    }

    /**
     * 挂载弹窗方法
     */
    public void mount(BasePanel dialog) {
        if (!initialized) {
            throw new RuntimeException("请先初始化");
        }

        interactiveListener.stopListener();

        /// 渲染弹窗UI
        dialog.render();

        /// 创建监听器
        Panel dialogWindows = new Panel(0, 0, terminalWidth, terminalHeight);
        dialogWindows.addComponent(dialog);

        InteractiveListener dialogListener = new InteractiveListener(dialogWindows);
        dialog.setBindData(dialogListener); /// 为弹窗绑定监听器

        dialogListener.start();

        /// 恢复界面并启动监听器
        dialog.clear();
        writer.flush();
        start();
    }

    public void start() {
        render();
        interactiveListener.start();
    }

    public void stop() {
        if (!initialized) {
            throw new RuntimeException("请先初始化");
        }
        interactiveListener.stopListener();
    }

    /**
     * 渲染方法
     */
    public void render() {
        if (debug) {
            /// TODO: debug面板开发
            System.out.println("你开启了debug模式");
        }

        this.rootPanel.render();
    }

    /**
     * Debug模式开启
     */
    public void enableDebug() {
        debug = true;
    }

    /**
     * Debug模式关闭
     */
    public void disableDebug() {
        debug = false;
    }

}
