package cn.ubibi.commons.ssp;


import cn.ubibi.commons.ssp.mo.MapKeyValue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleSerializeProtoManager {

    private final Map<Integer, Class> simpleSerializableIdClassMap = new ConcurrentHashMap<>();
    private final Map<Class, Integer> simpleSerializableClassIdMap = new ConcurrentHashMap<>();

    public SimpleSerializeProtoManager() {
        this.addClassInner(-1001, MapKeyValue.class);
    }

    public void addClass(Integer clazzId, Class clazz) throws Exception {
        if (clazzId < 0) {
            throw new Exception("classId must greater then 0 ");
        }
        this.addClassInner(clazzId,clazz);
    }


    private void addClassInner(Integer clazzId, Class clazz) {
        simpleSerializableClassIdMap.put(clazz, clazzId);
        simpleSerializableIdClassMap.put(clazzId, clazz);
    }


    public Integer getClassId(Class clazz) {
        return simpleSerializableClassIdMap.get(clazz);
    }

    public Class getClassById(Integer clazzId) {
        return simpleSerializableIdClassMap.get(clazzId);
    }


}
