package io.yukk1o.easytermui.util;

import java.util.Map;

public class MapUtils {
    public static int getValueSum(Map<String, Integer> map) {
        int sum = 0;
        for (Integer value : map.values()) {
            sum += value;
        }
        return sum;
    }
}
