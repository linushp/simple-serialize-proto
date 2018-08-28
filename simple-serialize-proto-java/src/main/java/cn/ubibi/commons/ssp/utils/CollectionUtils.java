package cn.ubibi.commons.ssp.utils;

import java.util.Collection;
import java.util.Map;

public class CollectionUtils {
    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Object[] stringArray) {
        return stringArray == null || stringArray.length == 0;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

}
