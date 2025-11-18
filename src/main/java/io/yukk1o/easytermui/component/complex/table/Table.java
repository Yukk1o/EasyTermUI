package io.yukk1o.easytermui.component.complex.table;

import io.yukk1o.easytermui.base.BaseComponent;
import io.yukk1o.easytermui.base.BasePanel;
import io.yukk1o.easytermui.component.Text;
import io.yukk1o.easytermui.util.AnsiUtils;
import io.yukk1o.easytermui.util.MapUtils;
import io.yukk1o.easytermui.util.reflect.ObjectReflectUtils;

import java.util.*;

public class Table extends BasePanel {
    Object[] data;
    LinkedHashMap<String, Integer> columnMeta;

    public static class TableCell {
        private final Object data;
        private final int columnWidth;

        public TableCell(Object data, int columnWidth) {
            this.columnWidth = columnWidth;
            this.data = data;
        }

        public Object getData() {
            return data;
        }

        public int getColumnWidth() {
            return columnWidth;
        }
    }

    /**
     * 表格构造函数
     *
     * @param relX       相对坐标
     * @param relY       相对坐标
     * @param rows       行数(仅统计实际存储数据的行，不包含表头行和底部收尾行)
     * @param columnMeta (Key=列名，Value=该列的宽度)
     */
    public Table(int relX, int relY, int rows, LinkedHashMap<String, Integer> columnMeta) {
        /// MapUtils.getValueSum(columnMeta) + columnMeta.size() - 1 表示列宽(列宽 + 分割符)之和
        super(relX, relY, MapUtils.getValueSum(columnMeta) + columnMeta.size() - 1, rows + 3);

        if (rows <= 0) {
            throw new IllegalArgumentException("rows must be greater than 0");
        }

        /// 该组件最多存储 rows 行数据
        data = new Object[rows];
        /// 列名
        this.columnMeta = columnMeta;

        /// 添加表头组件
        addComponent(new TableRow(0, 0, width, converMapTOTableCellList(columnMeta), null));

        /// 添加分割线文本
        String line = "‾".repeat(width);
        addComponent(new Text(0, 1, width, 1, line));
        addComponent(new Text(0, rows + 2, width, 1, line));
    }

    /**
     * 设置表格数据
     *
     * @param dataSource 表格数据源
     */
    public <E> void setData(List<E> dataSource) {
        if (dataSource == null || dataSource.isEmpty()) {
            /// 清空数据
            if (!(this.data.length == 0)) {
                Arrays.fill(this.data, null);
            }
            /// 创建提示文本
            Text error = new Text(0, 2, width / 2 - 9, 1, "数据源为空！");
            /// 添加提示文本
            if (!(this.getChildren().size() == 4 && (this.getChildren().get(3) instanceof Text))) {
                removeDataRows();
                addComponent(error);
            }
            removeDataRows();
            addComponent(error);
            return;
        }
        LinkedHashMap<String, Object> fieldValues = ObjectReflectUtils.getFieldValues(dataSource.get(0));

        /// 数据源字段数量与列数量不一致
        if (fieldValues.size() != columnMeta.size()) {
            throw new IllegalArgumentException("数据源字段数量与列数量不一致！");
        }

        if (!(this.data.length == 0)) {
            Arrays.fill(this.data, null);
        }
        int actualDataSize = Math.min(dataSource.size(), this.data.length);
        for (int i = 0; i < actualDataSize; i++) {
            if (dataSource.get(i) == null) {
                continue;
            }

            this.data[i] = dataSource.get(i);
        }
    }

    @Override
    public void renderContext() {
        /*
           清空子组件并清屏
         */
        removeDataRows();
        AnsiUtils.clear(this.absY + 2, this.absY + this.height, this.absX, this.absX + this.width);

        for (int i = 0; i < data.length; i++) {
            if (data[i] == null) {
                continue;
            }
            Object[] columnWidths = columnMeta.values().toArray();
            Object[] fieldValues = ObjectReflectUtils.getFieldValues(data[i]).values().toArray();

            List<TableCell> rowCells = new LinkedList<>();
            for (int j = 0; j < columnWidths.length; j++) {
                Object cellData = j < fieldValues.length ? fieldValues[j] : "null";
                int width = (Integer) columnWidths[j];
                rowCells.add(new TableCell(cellData, width));
            }

            addComponent(new TableRow(0, i + 2, this.width, rowCells, data[i]));
        }

        super.renderContext();
    }

    private void removeDataRows() {
        getChildren().stream().skip(3).forEach(component -> removeComponent((BaseComponent) component));
    }

    private List<TableCell> converMapTOTableCellList(LinkedHashMap<String, Integer> map) {
        ArrayList<TableCell> cellList = new ArrayList<>();
        map.forEach((key, value) -> {
            cellList.add(new TableCell(key, value));
        });
        return cellList;
    }


}
