package io.yukk1o.easytermui.component.complex.table;


import io.yukk1o.easytermui.base.BaseComponent;
import io.yukk1o.easytermui.base.BasePanel;
import io.yukk1o.easytermui.component.Text;

import java.util.List;


public class TableRow extends BasePanel {
    private List<Table.TableCell> rowCells;

    /**
     * 构造「嵌套Panel」（相对坐标）
     *
     * @param relX   相对于父Panel的X坐标
     * @param relY   相对于父Panel的Y坐标
     * @param width  面板宽度
     */
    public TableRow(int relX, int relY, int width, List<Table.TableCell> rowCells, Object data) {
        super(relX, relY, width, 1);
        this.rowCells = rowCells;
        this.bindData = data;

        renderCell();
    }

    private void renderCell() {
        int posX = 0;

        for (Table.TableCell cell : this.rowCells) {
            Object cellData = cell.getData();
            int columnWidth = cell.getColumnWidth();

            if (cellData instanceof BaseComponent component) {
                component.setRelX(posX);
                component.setRelY(0);
                component.setBindData(this.bindData);
                addComponent(component);
            } else {
                String text = cellData == null ? "null" : cellData.toString();
                Integer textWidth = columnWidth;
                Text component = new Text(posX, 0, textWidth, 1, text);
                addComponent(component);
            }

            posX += columnWidth + 1;
        }
    }
}
