package io.yukk1o;

import io.yukk1o.easytermui.base.BasePanel;
import io.yukk1o.easytermui.base.Listener.InteractiveListener;
import io.yukk1o.easytermui.component.Button;
import io.yukk1o.easytermui.component.Text;

public class testDialog extends BasePanel {


    /**
     * 构造「嵌套Panel」（相对坐标）
     *
     * @param relX   相对于父Panel的X坐标
     * @param relY   相对于父Panel的Y坐标
     */
    public testDialog(int relX, int relY) {
        super(relX, relY, 40, 20);
        init();
    }

    public void init(){
        Text testText = new Text(0, 5, 40, 1, "测试弹窗");

        addComponent(testText);


        Button back = new Button(0, 4, "取消", (button) -> {
            System.out.println("取消");
            InteractiveListener listener = (InteractiveListener) this.getBindData();
            listener.stopListener();
        });

        addComponent(back);
    }

    @Override
    public void renderContext() {
        clear();

        super.renderContext();
    }
}
