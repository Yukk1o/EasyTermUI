package io.yukk1o.easyTermUi.base;

import io.yukk1o.easyTermUi.component.Panel;
import org.jline.utils.NonBlockingReader;

import java.io.IOException;

import static io.yukk1o.easyTermUi.EasyTermUI.activeInputBox;
import static io.yukk1o.easyTermUi.EasyTermUI.terminal;


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
        int b = 0, x = 0, y = 0;
        if (reader.ready()) b = reader.read(); // 读取<b>
        if (reader.ready()) x = reader.read() - 32; // 读取<x>
        if (reader.ready()) y = reader.read() - 32; // 读取<y>

        if ((b & 3) != 3 && (b & 64) == 0) {
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