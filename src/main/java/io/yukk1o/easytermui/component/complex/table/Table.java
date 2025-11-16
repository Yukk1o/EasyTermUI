package io.yukk1o.easytermui.component.complex.table;

import io.yukk1o.easytermui.base.BaseComponent;
import io.yukk1o.easytermui.base.BasePanel;
import io.yukk1o.easytermui.component.Text;
import io.yukk1o.easytermui.util.AnsiUtils;
import io.yukk1o.easytermui.util.MapUtils;
import io.yukk1o.easytermui.util.PrintUtils;
import io.yukk1o.easytermui.util.reflect.ObjectReflectUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class Table extends BasePanel {
    Object[] data;
    LinkedHashMap<String, Integer> columnMeta;

    /**
     * 表格构造函数
     *
     * @param relX   相对坐标
     * @param relY   相对坐标
     * @param rows   行数(仅统计实际存储数据的行，不包含表头行和底部收尾行)
     * @param columnMeta (Key=列名，Value=该列的宽度)
     */
    public Table(int relX, int relY, int rows, LinkedHashMap<String, Integer> columnMeta) {
        /// MapUtils.getValueSum(columnMeta) + columnMeta.size() - 1 表示列宽(列宽 + 分割符)之和
        super(relX, relY, MapUtils.getValueSum(columnMeta) + columnMeta.size() - 1, rows + 2);

        if (rows <= 0) {
            throw new IllegalArgumentException("rows must be greater than 0");
        }

        /// 该组件最多存储 rows 行数据
        data = new Object[rows];
        /// 列名
        this.columnMeta = columnMeta;

        /// 添加表头组件
        int posX = 0;
        int p = 26;

        addComponent(new TableRow(0, 0, width, new LinkedHashMap<>(columnMeta), null));

        /// 添加分割线文本
        String line = "‾".repeat(width);
        addComponent(new Text(0, 1, width, 1, line));
    }

    /**
     * 设置表格数据
     *
     * @param data 表格数据源
     */
public <E> void setData(List<E> data) {
//    if (data == null || data.isEmpty()) {
//        throw new IllegalArgumentException("数据源不能为空！");
//    }

    LinkedHashMap<String, Object> fieldValues = ObjectReflectUtils.getFieldValues(data.get(0));

    /// 数据源字段数量与列数量不一致
    if (fieldValues.size() != columnMeta.size()) {
        throw new IllegalArgumentException("数据源字段数量与列数量不一致！");
    }

    if (!(this.data.length == 0)) {
        Arrays.fill(this.data, null);
        }
    int actualDataSize = Math.min(data.size(), this.data.length);
    for (int i = 0; i < actualDataSize; i++) {
        if (data.get(i) == null) {
            continue;
        }

        this.data[i] = data.get(i);
    }
}



    @Override
    public void renderContext() {
        /*
           清空子组件并清屏
         */
        getChildren().stream().skip(2).forEach(component -> removeComponent((BaseComponent) component));
        AnsiUtils.clear(this.absY + 2, this.absY + this.height, this.absX, this.absX + this.width);

        for (int i = 0; i < data.length; i++) {
            if (data[i] == null) {
                continue;
            }
            Object[] cloWidth = columnMeta.values().toArray();
            Object[] values = ObjectReflectUtils.getFieldValues(data[i]).values().toArray();

            LinkedHashMap<Object, Integer> columnValues = new LinkedHashMap<>();
            int relX = 0;

            for (int j = 0; j < cloWidth.length; j++) {
                Object value = values[j];
                columnValues.put(values[j], (Integer) cloWidth[j]);
            }

            addComponent(new TableRow(0, i + 2, this.width, columnValues, data[i]));
            }

        super.renderContext();
    }

}
