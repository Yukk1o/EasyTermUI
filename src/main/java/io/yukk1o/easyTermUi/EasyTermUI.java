package io.yukk1o.easyTermUi;

import io.yukk1o.easyTermUi.components.InputBox;
import io.yukk1o.easyTermUi.components.Panel;
import io.yukk1o.easyTermUi.thread.InteractiveListener;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.io.PrintWriter;

public class EasyTermUI {
    /// 终端
    public static final Terminal terminal;
    /// 初始化
    public static Boolean initialized = false;
    /// 激活组件
    public static InputBox activeInputBox;
    /// 终端宽度
    /// 输出器
    public static PrintWriter writer;

    static {
        try {
            terminal = TerminalBuilder.builder()
                    .system(true)
                    .provider("jni")
                    .name("")
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        terminal.trackMouse(Terminal.MouseTracking.Normal);
        writer = terminal.writer();
        /// 清屏
        terminal.puts(InfoCmp.Capability.clear_screen);
        writer.flush();
    }

    /// 根面板
    public Panel rootPanel;
    int terminalHeight = terminal.getHeight();
    /// 终端高度
    int terminalWidth = terminal.getWidth();
    /// Debug模式开关
    private Boolean debug = false;

    /**
     * 构建方法
     */
    public EasyTermUI init(Panel rootPanel) {
        if (initialized) {
            return this;
        }

        /// 初始化终端大小
        int height = terminal.getHeight();
        int width = terminal.getWidth();
        /// 检测终端大小是否符合面板要求
        if (height < rootPanel.getHeight() || width < rootPanel.getWidth()) {
            String errorMessage = String.format("您的终端大小不足够\n请设置终端大小为 %d * %d\n", rootPanel.getWidth(),
                    rootPanel.getHeight());
            /// TODO: 将来添加弹窗
        }


        this.rootPanel = rootPanel;
        initialized = true;
        render();
        new InteractiveListener(this.rootPanel).start();
        return this;
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
