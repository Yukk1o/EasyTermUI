package io.yukk1o.easytermui.component.complex.table;


import io.yukk1o.easytermui.base.BasePanel;
import io.yukk1o.easytermui.component.Text;

import java.util.LinkedHashMap;



public class TableRow extends BasePanel {
    LinkedHashMap<String, Integer> columnValues;

    /**
     * 构造「嵌套Panel」（相对坐标）
     *
     * @param relX   相对于父Panel的X坐标
     * @param relY   相对于父Panel的Y坐标
     * @param width  面板宽度
     * @param columnValues 数据 -> 列宽
     */
    public TableRow(int relX, int relY, int width, LinkedHashMap<String, Integer> columnValues) {
        super(relX, relY, width, 1);
        this.columnValues = columnValues;

        int posX = 0;
        int p = 26;
        for (Object columnValue : columnValues.keySet()) {
            String value = columnValue.toString();
            Integer textWidth = columnValues.get(value);

            Text text = new Text(posX, 0, textWidth, 1, value);
            addComponent(text);
            posX += textWidth + 1;
        }
    }
}
