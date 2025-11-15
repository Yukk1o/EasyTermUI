package io.yukk1o.easytermui.util.reflect;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

public class ObjectReflectUtils {
    /**
     * 获取对象属性值
     *
     * @param obj 对象
     * @return 属性名 -> 属性值
     */
    public static LinkedHashMap<String, Object> getFieldValues(Object obj) {
        try {
            LinkedHashMap<String, Object> fieldValues = new LinkedHashMap<>();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                Object value = field.get(obj);
                fieldValues.put(name, value);
            }
            return fieldValues;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return new LinkedHashMap<>();
    }
}
