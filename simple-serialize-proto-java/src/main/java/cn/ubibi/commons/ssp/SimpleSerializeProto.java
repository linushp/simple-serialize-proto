package cn.ubibi.commons.ssp;

import cn.ubibi.commons.ssp.annotation.BeanFieldWithAnnotation;
import cn.ubibi.commons.ssp.annotation.BeanFieldWithAnnotationUtils;
import cn.ubibi.commons.ssp.annotation.SimpleSerializable;
import cn.ubibi.commons.ssp.mo.MapKeyValue;
import cn.ubibi.commons.ssp.utils.StreamingUtils;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;

public class SimpleSerializeProto {


    public static byte[] toByteArray(Object object) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        if (object == null) {
            StreamingUtils.writeTLBool(true, outputStream); //是否为null
            byte[] x = outputStream.toByteArray();
            outputStream.close();
            return x;
        }


        Integer classId = SimpleSerializeProtoManager.getClassId(object.getClass());
        if (classId == null) {
            throw new Exception("classId is not exists : " + object.getClass().getName());
        }

        StreamingUtils.writeTLBool(false, outputStream); //是否为null
        StreamingUtils.writeInt(classId, outputStream); //classId


        Class clazz = object.getClass();
        List<BeanFieldWithAnnotation> fields = BeanFieldWithAnnotationUtils.getBeanSimpleSerializeFields(clazz);

        StreamingUtils.writeInt(fields.size(), outputStream); //字段个数


        for (BeanFieldWithAnnotation field : fields) {

            StreamingUtils.writeInt(field.getIndex(), outputStream); //字段索引


            Object value = field.getField().get(object);
            Class<?> fieldType = field.getField().getType();

            if (Boolean.TYPE == fieldType || Boolean.class == fieldType) {
                StreamingUtils.writeTLBool((Boolean) value, outputStream);
            } else if (Integer.TYPE == fieldType || Integer.class == fieldType) {
                StreamingUtils.writeInt((Integer) value, outputStream);
            } else if (Long.TYPE == fieldType || Long.class == fieldType) {
                StreamingUtils.writeLong((Long) value, outputStream);
            } else if (Short.TYPE == fieldType || Short.class == fieldType) {
                StreamingUtils.writeInt((Integer) value, outputStream);
            } else if (Double.TYPE == fieldType || Double.class == fieldType) {
                StreamingUtils.writeDouble((Double) value, outputStream);
            } else if (Float.TYPE == fieldType || Float.class == fieldType) {
                StreamingUtils.writeDouble((Double) value, outputStream);
            } else if (Character.TYPE == fieldType || Character.class == fieldType) {
                if (value == null) {
                    value = "";
                }
                StreamingUtils.writeTLString(String.valueOf(value), outputStream);
            } else if (Byte.TYPE == fieldType || Byte.class == fieldType) {
                StreamingUtils.writeByte((Byte) value, outputStream);
            } else if (String.class == fieldType) {
                StreamingUtils.writeTLString((String) value, outputStream);
            } else if (byte[].class == fieldType) {
                StreamingUtils.writeTLBytes((byte[]) value, outputStream);
            } else if (Collection.class.isAssignableFrom(fieldType)) {

                Collection subCollection = (Collection) value;
                int elementCount = subCollection == null ? 0 : subCollection.size();
                byte[] subBytes = toByteArray(subCollection);
                StreamingUtils.writeInt(elementCount, outputStream);
                StreamingUtils.writeTLBytes(subBytes, outputStream);

            } else if (Map.class.isAssignableFrom(fieldType)) {

                Map subMap = (Map) value;
                int elementCount = subMap == null ? 0 : subMap.size();
                List<MapKeyValue> subKeyValueList = toMapKeyValueList(subMap);
                byte[] subBytes = toByteArray(subKeyValueList);

                StreamingUtils.writeInt(elementCount, outputStream);
                StreamingUtils.writeTLBytes(subBytes, outputStream);

            } else if (SimpleSerializable.class.isAssignableFrom(fieldType)) {
                byte[] subObjectBytes = toByteArray(value);
                StreamingUtils.writeTLBytes(subObjectBytes, outputStream);
            } else {
                //未知类型的
                StreamingUtils.writeInt(666666, outputStream);
            }
        }

        byte[] bytes = outputStream.toByteArray();
        outputStream.close();
        return bytes;
    }

    private static List<MapKeyValue> toMapKeyValueList(Map subMap) {
        if (subMap == null || subMap.isEmpty()) {
            return null;
        }

        List<MapKeyValue> keyValues = new ArrayList<>();

        Set<Map.Entry> entrys = subMap.entrySet();
        for (Map.Entry entry : entrys) {
            Object key = entry.getKey();
            if (key == null) {
                key = "";
            }
            SimpleSerializable value = (SimpleSerializable) entry.getValue();
            key = key.toString();
            MapKeyValue x = new MapKeyValue();
            x.setKey(key.toString());
            if (value != null) {
                x.setValue(value);
            }

            keyValues.add(x);
        }
        return keyValues;
    }


    private static byte[] toByteArray(Collection subCollection) throws Exception {
        if (subCollection == null || subCollection.isEmpty()) {
            return new byte[]{};
        }
        ByteArrayOutputStream subOutputStream = new ByteArrayOutputStream();
        for (Object subObject : subCollection) {
            byte[] subBytes2 = toByteArray(subObject);
            StreamingUtils.writeTLBytes(subBytes2, subOutputStream);
        }
        byte[] subBytes1 = subOutputStream.toByteArray();
        subOutputStream.close();
        return subBytes1;
    }


    public static <T> T parseObject(byte[] bytes) throws Exception {

        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        boolean isNull = StreamingUtils.readTLBool(inputStream); //是否为null
        if (isNull) {
            return null;
        }

        int classId = StreamingUtils.readInt(inputStream);

        Class tClass = SimpleSerializeProtoManager.getClassById(classId);
        Object objectInstance = tClass.newInstance();


        int fieldCount = StreamingUtils.readInt(inputStream);

        for (int i = 0; i < fieldCount; i++) {

            int fieldIndex = StreamingUtils.readInt(inputStream);
            BeanFieldWithAnnotation xx = BeanFieldWithAnnotationUtils.getFieldTypeByIndex(fieldIndex, tClass);

            Class targetClass = xx.getType();

            Object value = null;

            if (Boolean.TYPE == targetClass || Boolean.class == targetClass) {
                value = StreamingUtils.readTLBool(inputStream);
            } else if (Integer.TYPE == targetClass || Integer.class == targetClass) {
                value = StreamingUtils.readInt(inputStream);
            } else if (Long.TYPE == targetClass || Long.class == targetClass) {
                value = StreamingUtils.readLong(inputStream);
            } else if (Short.TYPE == targetClass || Short.class == targetClass) {
                value = StreamingUtils.readInt(inputStream);
            } else if (Double.TYPE == targetClass || Double.class == targetClass) {
                value = StreamingUtils.readDouble(inputStream);
            } else if (Float.TYPE == targetClass || Float.class == targetClass) {
                value = StreamingUtils.readDouble(inputStream);
            } else if (Character.TYPE == targetClass || Character.class == targetClass) {
                value = StreamingUtils.readTLString(inputStream);
            } else if (Byte.TYPE == targetClass || Byte.class == targetClass) {
                value = StreamingUtils.readByte(inputStream);
            } else if (String.class == targetClass) {
                value = StreamingUtils.readTLString(inputStream);
            } else if (byte[].class == targetClass) {
                value = StreamingUtils.readTLBytes(inputStream);
            } else if (Collection.class.isAssignableFrom(targetClass)) {

                int subElementCount = StreamingUtils.readInt(inputStream);
                byte[] subBytes = StreamingUtils.readTLBytes(inputStream);

                value = parseObjectList(subElementCount, subBytes);
                value = collectionTypeCast((List) value, targetClass);


            } else if (Map.class.isAssignableFrom(targetClass)) {

                int subElementCount = StreamingUtils.readInt(inputStream);
                byte[] subBytes = StreamingUtils.readTLBytes(inputStream);

                List mapKeyValueList = parseObjectList(subElementCount, subBytes);
                value = mapKeyValueListToMap(mapKeyValueList);

            } else if (SimpleSerializable.class.isAssignableFrom(targetClass)) {
                byte[] subBytes = StreamingUtils.readTLBytes(inputStream);
                value = parseObject(subBytes);//classId
            } else {
                //未知类型的
                StreamingUtils.readInt(inputStream); //666666
            }

            xx.setBeanValue(objectInstance, value);
        }


        inputStream.close();
        return (T) objectInstance;
    }


    private static Map<String, Object> mapKeyValueListToMap(List mapKeyValueList) {
        if (mapKeyValueList == null) {
            return null;
        }

        Map<String, Object> result = new HashMap<>();
        for (Object kv : mapKeyValueList) {
            MapKeyValue mapKeyValue = (MapKeyValue) kv;
            result.put(mapKeyValue.getKey(), mapKeyValue.getValue());
        }
        return result;
    }


    private static Object collectionTypeCast(List value, Class targetClass) {
        if (value == null) {
            return null;
        }

        if (targetClass == List.class || targetClass == ArrayList.class) {
            return value;
        } else if (targetClass == LinkedList.class) {
            return new LinkedList(value);
        } else if (targetClass == Set.class || targetClass == HashSet.class) {
            return new HashSet(value);
        } else if (targetClass == TreeSet.class) {
            return new TreeSet(value);
        } else if (targetClass == LinkedHashSet.class) {
            return new LinkedHashSet<>(value);
        }
        return null;
    }


    private static List parseObjectList(int subElementCount, byte[] subBytes1) throws Exception {
        if (subElementCount == 0) {
            return null;
        }

        ByteArrayInputStream subByteArrayInputStream = new ByteArrayInputStream(subBytes1);
        List subList = new ArrayList();
        for (int sss = 0; sss < subElementCount; sss++) {
            byte[] subBytes2 = StreamingUtils.readTLBytes(subByteArrayInputStream);
            Object subObject = parseObject(subBytes2);
            subList.add(subObject);
        }
        subByteArrayInputStream.close();
        return subList;
    }


}
