package cn.ubibi.commons.ssp;

import cn.ubibi.commons.ssp.SimpleSerializeProto;
import cn.ubibi.commons.ssp.SimpleSerializeProtoManager;

public class SimpleSerializeUtil {
    private static final SimpleSerializeProtoManager simpleSerializeProtoManager = new SimpleSerializeProtoManager();
    private static final SimpleSerializeProto simpleSerializeProto = new SimpleSerializeProto(simpleSerializeProtoManager);

    public static void addClass(Integer classId,Class clazz) throws Exception {
        simpleSerializeProtoManager.addClass(classId,clazz);
    }

    public static byte[] toByteArray(Object obj) throws Exception {
        return simpleSerializeProto.toByteArray(obj);
    }

    public static Object parseObject(byte[] bytes) throws Exception {
        return simpleSerializeProto.parseObject(bytes);
    }
}
