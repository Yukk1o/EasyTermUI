package io.yukk1o;

import io.yukk1o.easytermui.EasyTermUI;
import io.yukk1o.easytermui.component.Button;
import io.yukk1o.easytermui.component.InputBox;
import io.yukk1o.easytermui.component.Panel;
import io.yukk1o.easytermui.component.complex.table.Table;
import io.yukk1o.easytermui.util.PrintUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static io.yukk1o.easytermui.EasyTermUI.writer;

public class Main {
    public static void main(String[] args) throws IOException {

        initializeInterface();

        Panel rootPanel = new Panel(0, 0, 50, 50);

        /// 0
        rootPanel.addComponent(new Button(5, 12, "登录", () -> PrintUtils.printAt(5, 14, "登录成功")
        ));

        /// 1
        rootPanel.addComponent(new Button(20, 12, "清屏", rootPanel::clear));

        /// 2
        rootPanel.addComponent(new InputBox(14, 7, 16));

        Table table = getTable();
        rootPanel.addComponent(table);




        /// 3
        rootPanel.addComponent(new InputBox(14, 9, 16));

        new EasyTermUI().init(rootPanel);

        while (true) {
            try {
                Thread.sleep(1000); // 模拟耗时任务
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }




    }

    private static Table getTable() {
        LinkedHashMap<String, Integer> columnMeta = new LinkedHashMap<>();
        columnMeta.put("编号", 5);
        columnMeta.put("书名", 10);
        columnMeta.put("作者", 10);
        columnMeta.put("价格", 5);

        Table table = new Table(0, 15, 5, columnMeta);

        List<testEntity> data = new ArrayList<>();
        testEntity book1 = new testEntity(1, "《算法导论》", "R.L.Rivest", 100);
        data.add(book1);
        testEntity book2 = new testEntity(2, "《数据结构》", "R.L.Rivest", 80);
        data.add(book2);
        testEntity book3 = new testEntity(3, "《计算机网络》", "R.L.Rivest", 60);
        data.add(book3);
        testEntity book4 = new testEntity(4, "《软件工程》", "R.L.Rivest", 40);
        data.add(book4);

        table.setData(data);
        return table;
    }

    private static void initializeInterface(){
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