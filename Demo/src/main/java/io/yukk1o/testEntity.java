package io.yukk1o;

import io.yukk1o.easytermui.component.Button;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class testEntity {
    private Integer id;
    private String name;
    private String author;
    private Integer price;
    private Button button = new Button(0,0,"我是大帅哥", (button1) -> {
        System.out.println("我在测试");
    });
}
