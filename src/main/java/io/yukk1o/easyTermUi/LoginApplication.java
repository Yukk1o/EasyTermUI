package io.yukk1o.easyTermUi;

import io.yukk1o.easyTermUi.components.Button;
import io.yukk1o.easyTermUi.components.InputBox;
import io.yukk1o.easyTermUi.components.Panel;
import io.yukk1o.easyTermUi.thread.InteractiveListener;

import java.io.IOException;

import static io.yukk1o.easyTermUi.EasyTermUI.*;

public class LoginApplication {

    public static void main(String[] args) throws IOException {

        initializeInterface();

        Panel rootPanel = new Panel(0, 0, 100, 100);
        rootPanel.addComponent(new Button(5, 12, "登录", () -> System.out.println("我是测试")));
        rootPanel.addComponent(new Button(20, 12, "注册", () -> {
            terminal.writer().println("终于写完啦!!!");
            terminal.flush();
        }));
        rootPanel.addComponent(new InputBox(14, 7, 16));
        rootPanel.addComponent(new InputBox(14, 9, 16));

        Panel rooPanel = new Panel(2, 30, 30, 20);
        rooPanel.addComponent(new Button(5, 5, "登录", () -> System.out.println("我是测试")));
        rooPanel.addComponent(new Button(20, 5, "注册", () -> {
            terminal.writer().println("终于写完啦!!!");
            terminal.flush();
        }));
        rooPanel.addComponent(new InputBox(14, 1, 16));
        rooPanel.addComponent(new InputBox(14, 3, 16));


        rootPanel.addComponent(rooPanel);

        EasyTermUI gui = new EasyTermUI().init(rootPanel);

        new InteractiveListener(rootPanel).start();

        while (true) {
            try {
                Thread.sleep(1000); // 模拟耗时任务
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

    }

    private static void initializeInterface() {
        writer.println("=================================");
        writer.println("            图书管理系统");
        writer.println();
        writer.println("             登录界面");
        writer.println("=================================");
        writer.println("");
        writer.printf("\t账号: %-16s\n", "");
        writer.println("\t    ");
        writer.printf("\t密码: %-16s\n", "");
        writer.println();
        writer.println("+-------------------------------+");
        writer.println("|\t\t\t\t|");
        writer.println("+-------------------------------+");
    }
}