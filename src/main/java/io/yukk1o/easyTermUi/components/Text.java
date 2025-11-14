//package io.yukk1o.easyTermUi.components;
//
//import io.yukk1o.easyTermUi.base.BaseComponent;
//import io.yukk1o.easyTermUi.ulitis.AnsiUtils;
//import org.jline.utils.AttributedString;
//import org.jline.utils.AttributedStyle;
//
//import static io.yukk1o.easyTermUi.EasyTermUI.terminal;
//
//public class Text extends BaseComponent {
//    String[] line;
//
//
//    /**
//     * 文本构建函数
//     *
//     * @param relX 相对坐标
//     * @param relY 相对坐标
//     */
//    public Text(int relX, int relY, String text) {
//        super(relX, relY);
//        int availableWidth = parent.getWidth() - relX;
//        int width = 0;
//        /// 含换行符进行特殊处理
//        if (text.contains("\n")) {
//            this.line = text.split("\n");
//            ///
//            for (String s : line) {
//                width = Math.max(s.length(), width);
//            }
//
//
//        } else {
//            width = text.length();
//        }
//        Text();
//    }
//
//    public Text()
//
//    /**
//     * 多行文本构建函数
//     *
//     * @param relX  相对坐标
//     * @param relY  相对坐标
//     * @param text  文本内容
//     * @param width 单行宽度
//     */
//    public Text(int relX, int relY, String text, int width) {
//        super(relX, relY, text.length(), 1);
//    }
//
//    @Override
//    public void renderContext() {
//        AnsiUtils.moveCursor(absY, absX);
//        AttributedString buttonText = new AttributedString(
//                "text",
//                AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE).bold());
//
//        /// 绘制按钮
//        text.print(terminal);
//    }
//
//    @Override
//    public void handleClick(int clickAbsX, int clickAbsY) {
//        return;
//    }
//}
