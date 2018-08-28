package cn.ubibi.commons.ssp;

import cn.ubibi.commons.ssp.mo.MapKeyValue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleSerializeProtoManager {

    private static final Map<Integer, Class> simpleSerializableIdClassMap = new ConcurrentHashMap<>();
    private static final Map<Class, Integer> simpleSerializableClassIdMap = new ConcurrentHashMap<>();

    static {
        SimpleSerializeProtoManager.addClass(-1, MapKeyValue.class);
    }

    public static void addClass(Integer clazzId, Class clazz) {
        simpleSerializableClassIdMap.put(clazz, clazzId);
        simpleSerializableIdClassMap.put(clazzId, clazz);
    }

    public static Integer getClassId(Class clazz) {
        return simpleSerializableClassIdMap.get(clazz);
    }

    public static Class getClassById(Integer clazzId) {
        return simpleSerializableIdClassMap.get(clazzId);
    }


}
