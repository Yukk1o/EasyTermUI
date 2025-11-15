package io.yukk1o.easytermui.base.Listener;


import io.yukk1o.easytermui.component.Panel;
import org.jline.utils.NonBlockingReader;

import java.io.IOException;


import static io.yukk1o.easytermui.EasyTermUI.activeInputBox;
import static io.yukk1o.easytermui.EasyTermUI.terminal;

public class InteractiveListener {
    private final NonBlockingReader reader;
    private final Panel rootPanel;
    private boolean running = false;

    // 构造器：传入终端/按钮集合/输入框集合
    public InteractiveListener(Panel rootPanel) {
        reader = terminal.reader();
        this.rootPanel = rootPanel;
    }

    // 启动监听
    public void start() {
        if (running) return; // 避免重复启动
        running = true;

        try {
            while (running) {
                int c = reader.read();
                if (c == '\033') { // 检测到ESC开头
                    handleEscapeSequence();
                } else if (activeInputBox != null && activeInputBox.isEditing()) {
                    activeInputBox.insert(c);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleEscapeSequence() throws IOException {
        /// 检测开始前缀
        int secondChar = reader.read();
        if (reader.ready() && (secondChar == '[' || secondChar == 'O')) {
            if (!reader.ready()) {
                return;
            }

            int flag = reader.read();

            /// 鼠标点击事件
            if (flag == 'M') {
                mouseClick(reader);
                return;
            }

            /// 检测方向序列
            if (flag >= 'A' && flag <= 'D') {
                handleDirectionKey(flag);
            }
        }
    }

    private void mouseClick(NonBlockingReader reader) throws IOException {
        // 等待并读取鼠标事件的三个字节
        int b = reader.read(50); // 按钮状态
        int x = reader.read(50) - 32; // X坐标
        int y = reader.read(50) - 32; // Y坐标

        // 检查读取是否成功
        if (b == -1 || x == -1 || y == -1) {
            return; // 读取超时或失败
        }

        // 检查是否为鼠标按下事件
        if ((b & 3) != 3) {
            rootPanel.onClick(x, y);
        }
    }


    /**
     * 方向键移动逻辑（自定义键位映射）
     * 注：标准 ANSI 序列中 C=右、D=左
     * 当前按业务需求调整为以下映射
     * - A: 上方向键（输入框场景暂不响应，仅占位）
     * - B: 下方向键（输入框场景暂不响应，仅占位）
     * - C: 左方向键（光标向左移动）
     * - D: 右方向键（光标向右移动）
     */
    private void handleDirectionKey(int key) {
        if (activeInputBox != null) {
            activeInputBox.moveCursor(key);
        }
    }
}