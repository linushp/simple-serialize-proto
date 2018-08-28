package cn.ubibi.commons.ssp.utils;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

public class CastBasicTypeUtils {

    private static final Integer integer_0 = 0;
    private static final Long long_0 = 0L;
    private static final String empty_str = "";

    // 基本数据类型转换
    public static Object toBasicTypeOf(Object value, Class targetType) throws Exception {
        if (targetType == null) {
            return value;
        }
        return CastBasicTypeUtils.toTypeOf(value, targetType);
    }


    // 基本数据类型转换,返回一个  Array.newInstance
    public static Object toBasicTypeArrayOf(Collection collection, Class elementType) throws Exception {
        if (collection == null) {
            return null;
        }
        Object array_result = Array.newInstance(elementType, collection.size());
        int index = 0;
        for (Object obj : collection) {
            Object obj2 = toBasicTypeOf(obj, elementType);
            Array.set(array_result, index, obj2);
            index++;
        }
        return array_result;
    }


    // 基本数据类型转换,返回一个  Array.newInstance
    public static Collection toBasicTypeCollectionOf(Collection collection, Class<? extends Collection> targetClazz, Class elementType) throws Exception {
        if (collection == null) {
            return null;
        }
        Collection array_result = targetClazz.newInstance();
        for (Object obj : collection) {
            Object obj2 = obj;
            if (elementType != null) {
                obj2 = CastBasicTypeUtils.toBasicTypeOf(obj, elementType);
            }
            array_result.add(obj2);
        }
        return array_result;
    }

    private static <T> T toTypeOf(Object value, Class<T> targetType) throws Exception {
        if (isNull(value)) {
            return null;
        }

        Class valueType = value.getClass();
        if (targetType == valueType || targetType.equals(valueType)) {
            return (T) value;
        }


        Object result = null;
        if (targetType == String.class) {
            result = getStringValue(value);
        } else if (isTypeOf(targetType, Integer.class, Integer.TYPE)) {
            result = toInteger(value);
        } else if (isTypeOf(targetType, Float.class, Float.TYPE)) {
            result = toFloat(value);
        } else if (isTypeOf(targetType, Double.class, Double.TYPE)) {
            result = toDouble(value);
        } else if (isTypeOf(targetType, Long.class, Long.TYPE)) {
            result = toLong(value);
        } else if (isTypeOf(targetType, Boolean.class, Boolean.TYPE)) {
            result = toGeneralizedBoolean(value);
        } else if (isTypeOf(targetType, Short.class, Short.TYPE)) {
            result = toShort(value);
        } else if (isTypeOf(targetType, Byte.class, Byte.TYPE)) {
            result = toByte(value);
        } else if (isTypeOf(targetType, Character.class, Character.TYPE)) {
            result = toCharacter(value);
        } else if (targetType == Timestamp.class) {
            result = toTimestamp(value);
        } else if (targetType == Date.class) {
            result = toDate(value);
        } else if (targetType == BigDecimal.class) {
            result = toBigDecimal(value);
        } else if (targetType == BigInteger.class) {
            result = toBigInteger(value);
        } else {
            throw new Exception("NotSupportTheTypeOf:" + targetType);
        }

        return (T) result;
    }


    public static Character toCharacter(Object value) {
        if (isNull(value)) {
            return Character.MIN_VALUE;
        }

        if (value instanceof Character) {
            return (Character) value;
        }

        if (value instanceof String && !((String) value).isEmpty()) {
            String str = getStringValue(value);
            return str.charAt(0);
        }

        if (value instanceof Integer || value instanceof Short || value instanceof Long) {
            int v = toInteger(value);
            if (v <= Character.MAX_VALUE) {
                return (char) v;
            }
        }

        return Character.MIN_VALUE;
    }


    public static Short toShort(Object value) {
        if (isNull(value)) {
            return 0;
        }

        if (value instanceof Short) {
            return (Short) value;
        }

        return Short.valueOf(ignoreDotAfter(getStringValue(value)));
    }


    public static Integer toInteger(Object value) {
        if (isNull(value)) {
            return 0;
        }

        if (value instanceof Integer) {
            return (Integer) value;
        }

        return Integer.valueOf(ignoreDotAfter(getStringValue(value)));
    }


    public static Long toLong(Object value) {
        if (isNull(value)) {
            return 0L;
        }

        if (value instanceof Long) {
            return (Long) value;
        }

        return Long.valueOf(ignoreDotAfter(getStringValue(value)));
    }


    //byte 实际上是一个表示范围比较小的int（-128，127）
    public static Byte toByte(Object value) {

        if (isNull(value)) {
            return 0;
        }
        if (value instanceof Byte) {
            return (Byte) value;
        }

        if (value instanceof Integer) {
            int intValue = (int) value;
            return (byte) intValue;
        }

        return Byte.valueOf(ignoreDotAfter(getStringValue(value)));
    }


    /**
     * 忽略掉小数点及以后的字符串
     *
     * @param value 类似于：123.3223  .00
     * @return 无小数点的字符串
     */
    public static String ignoreDotAfter(String value) {
        String v = value;
        int indexOfDot = v.indexOf(".");
        if (indexOfDot == 0) {
            return "0";
        }
        if (indexOfDot > 0) {
            v = v.substring(0, indexOfDot);
        }
        return v;
    }


    public static Float toFloat(Object value) {
        if (isNull(value)) {
            return 0F;
        }
        if (value instanceof Float) {
            return (Float) value;
        }
        return Float.valueOf(getStringValue(value));
    }

    public static Double toDouble(Object value) {
        if (isNull(value)) {
            return 0D;
        }

        if (value instanceof Double) {
            return (Double) value;
        }

        return Double.valueOf(getStringValue(value));
    }

    public static Boolean toBoolean(Object value) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        }

        return "true".equalsIgnoreCase(getStringValue(value));
    }


    //广义的boolean类型,null,0,"" 都认为是false
    public static Boolean toGeneralizedBoolean(Object value) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        }

        if (isNull(value) || integer_0.equals(value) || long_0.equals(value) || empty_str.equals(value)) {
            return false;
        }

        String stringValue = getStringValue(value);
        if (StringUtils.isBlank(stringValue) || "0".equals(stringValue) || "false".equals(stringValue)) {
            return false;
        }

        return true;
    }


    public static BigInteger toBigInteger(Object value) {
        if (isNull(value)) {
            return new BigInteger("0");
        }

        if (value instanceof BigInteger) {
            return (BigInteger) value;
        }

        return new BigInteger(ignoreDotAfter(getStringValue(value)));
    }

    public static BigDecimal toBigDecimal(Object value) {
        if (isNull(value)) {
            return new BigDecimal(0);
        }

        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }

        return new BigDecimal(getStringValue(value));
    }


    public static Date toDate(Object value) {
        return toTimestamp(value);
    }


    public static Timestamp toTimestamp(Object value) {
        if (isNull(value)) {
            return null;
        }


        if (value instanceof Timestamp) {
            return (Timestamp) value;
        }

        long longDate = toLong(value);
        Timestamp timestamp = new Timestamp(longDate);
        return timestamp;
    }


    public static String getStringValue(Object value) {
        if (isNull(value)) {
            return null;
        }
        return value.toString();
    }


    public static boolean isNull(Object value) {
        return value == null;
    }


    /**
     * 判断数据类型是否需要转换
     *
     * @param fieldType
     * @param targetClass1
     * @param targetClass2
     * @return 是否
     */
    private static boolean isTypeOf(Class fieldType, Class targetClass1, Class targetClass2) {
        return fieldType == targetClass1 || fieldType == targetClass2;
    }

    public static boolean isEmptyString(Object value) {
        return getStringValue(value) == null || getStringValue(value).isEmpty();
    }


    /**
     * 八种基本数据类型
     *
     * @param type 类型
     * @return 是否是基本数据类型
     */
    public static boolean isBasicType(Class type) {
        if (type == Integer.TYPE || type == Integer.class ||
                type == Long.TYPE || type == Long.class ||
                type == Short.TYPE || type == Short.class ||
                type == Double.TYPE || type == Double.class ||
                type == Float.TYPE || type == Float.class ||
                type == Boolean.TYPE || type == Boolean.class ||
                type == Character.TYPE || type == Character.class ||
                type == Byte.TYPE || type == Byte.class
        ) {
            return true;
        }
        return false;
    }

}
