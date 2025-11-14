# EasyTermUI 🖥️
轻量易用的 Java 终端 UI 库（基于 JLine），主打「极简配置、组件化开发、开箱即用」—— 无需复杂上下文，一行 `render()` 直接渲染，快速构建终端应用。

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)
[![Java 17+](https://img.shields.io/badge/Java-17%2B-blue.svg)](https://www.oracle.com/java/technologies/downloads/)
[![JLine 3.x](https://img.shields.io/badge/JLine-3.23.0-orange.svg)](https://jline.org/)

## 🌟 核心特性
- **无参渲染**：组件自带 `render()` 方法，无需手动传递终端/坐标上下文；
- **轻量组件化**：基础组件可直接用，支持继承扩展自定义组件；
- **终端自适应**：自动识别终端尺寸，跨平台兼容（Windows/Linux/macOS）；
- **低学习成本**：API 直观，新手快速上手，无需终端底层知识。

## 🚀 快速开始
### 1. 环境要求
- JDK 17+
- Maven 3.x（或直接导入 JAR）
- 支持 ANSI 转义码的终端（现代终端默认支持）

### 2. 安装方式
#### 方式 1：Maven 依赖（本地安装）
```bash
# 克隆仓库
git clone https://github.com/yukk1o/EasyTermUI.git
cd EasyTermUI
# 编译安装到本地仓库
mvn clean install
```
在你的项目 `pom.xml` 中添加依赖：
```xml
<dependency>
    <groupId>io.yukk1o</groupId>
    <artifactId>easytermui</artifactId>
    <version>1.0.0</version>
</dependency>
```

#### 方式 2：直接导入 JAR
从 [Releases](https://github.com/yukk1o/EasyTermUI/releases) 下载 JAR 包，添加到项目类路径。

### 3. 基础示例（组件渲染）
```java
import io.yukk1o.easytermui.EasyTermUI;
import io.yukk1o.easytermui.base.BaseComponent;

public class Demo {
    public static void main(String[] args) {
        /// 创建根面板
        Panel rootPanel = new Panel(0, 0, 100, 100);

        /// 创建子组件
        rootPanel.addComponent(new Button(5, 12, "登录", () -> {
            terminal.writer().println("你在登录");
            terminal.flush();
        }));

        rootPanel.addComponent(new Button(20, 12, "注册", () -> {
            terminal.writer().println("你在注册");
            terminal.flush();
        }));

        rootPanel.addComponent(new InputBox(14, 7, 16));
        rootPanel.addComponent(new InputBox(14, 9, 16));

        /// 调用入口函数渲染UI并启动
        EasyTermUI easyTermUI = new EasyTermUI().init(rootPanel);
        easyTermUI.start;
    }
}
```

## 📚 核心使用
### 1. 基础组件（BaseComponent）
所有组件的基类，提供坐标、尺寸管理和渲染入口：
```java
// 创建组件：相对坐标 (x=2, y=4)，宽 30，高 2
BaseComponent component = new BaseComponent(2, 4, 30, 2) {
    @Override
    protected void renderContent() {
        // 自定义渲染内容（如多行文本、边框等）
        moveCursor(absY, absX);
        terminal.writer().write("基础组件示例");
        moveCursor(absY + 1, absX);
        terminal.writer().write("宽度 30 | 高度 2");
    }
};
component.render();
```

### 2. 面板组件（BasePanel）
组件容器，支持分组管理子组件：
```java
import io.yukk1o.easytermui.base.BasePanel;

// 创建面板：坐标 (2, 7)，宽 30，高 5，标题 "示例面板"
BasePanel panel = new BasePanel(2, 7, 30, 5, "示例面板") {
    @Override
    protected void layoutChildren() {
        // 子组件自动布局（示例：垂直排列）
        int y = 1;
        for (BaseComponent child : getChildren()) {
            child.setRelX(2);
            child.setRelY(y);
            y += child.getHeight() + 1;
        }
    }
};

// 添加子组件
panel.addComponent(new BaseComponent(0, 0, 20, 1) {
    @Override
    protected void renderContent() {
        moveCursor(absY, absX);
        terminal.writer().write("面板内子组件");
    }
});

panel.render();
```

### 3. 扩展自定义组件
继承 `BaseComponent` 实现专属功能（如按钮、列表）：
```java
public class CustomComponent extends BaseComponent {
    public CustomComponent(int relX, int relY, int width, int height) {
        super(relX, relY, width, height);
    }

    @Override
    protected void renderContent() {
        // 自定义渲染逻辑（如绘制边框、交互响应）
        moveCursor(absY, absX);
        terminal.writer().write("自定义组件");
    }

    // 可选：重写点击事件
    @Override
    protected void handleClick(int clickX, int clickY)
        System.out.println("组件被点击：(" + clickX + "," + clickY + ")");
    }
}
```

## 📁 项目结构
```
io.yukk1o.easytermui/
├── base/                # 核心基类（BaseComponent / BasePanel  / 事件监听）
├── component/           # 自带的基础组件(按钮 / 输入框)
├── constant/            # 基础常量定义
├── util/                # 工具类（终端操作）
└── EasyTermUI.java      # 库入口（初始化、全局配置）
```

## 🤝 贡献指南
1. Fork 本仓库；
2. 创建特性分支（`git checkout -b feature/xxx`）；
3. 提交代码（`git commit -m "添加 xxx 功能"`）；
4. 推送分支并打开 Pull Request。

## 📄 开源协议
基于 MIT 协议开源，可自由用于个人/商业项目。详见 [LICENSE](LICENSE)。

## 🔗 相关链接
- GitHub 仓库：[https://github.com/yukk1o/EasyTermUI](https://github.com/yukk1o/EasyTermUI)
- JLine 文档：[https://jline.org/docs](https://jline.org/docs)
- 作者：[yukk1o](https://github.com/yukk1o)
