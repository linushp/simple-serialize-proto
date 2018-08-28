package cn.ubibi.commons.ssp.annotation;

import cn.ubibi.commons.ssp.utils.BeanField;
import cn.ubibi.commons.ssp.utils.BeanFieldUtils;

import java.util.*;

public class BeanFieldWithAnnotationUtils {

    private static final Map<Class, List<BeanFieldWithAnnotation>> mapList = new HashMap<>();
    private static final Map<Class, Map<Integer, BeanFieldWithAnnotation>> fieldIndexMap = new HashMap<>();


    public static List<BeanFieldWithAnnotation> getBeanSimpleSerializeFields(Class clazz) {
        List<BeanFieldWithAnnotation> x = mapList.get(clazz);
        if (x == null) {
            synchronized (mapList) {
                x = getBeanSimpleSerializeFields1(clazz, SimpleSerializeField.class);
                mapList.put(clazz, x);
            }
        }
        return x;
    }


    private static List<BeanFieldWithAnnotation> getBeanSimpleSerializeFields1(Class clazz, Class<SimpleSerializeField> annotationClass) {
        List<BeanField> x = BeanFieldUtils.getBeanFields(clazz);
        List<BeanFieldWithAnnotation> result = new ArrayList<>();
        for (BeanField beanField : x) {
            SimpleSerializeField annotation = beanField.getField().getAnnotation(annotationClass);
            if (annotation != null) {
                BeanFieldWithAnnotation m = new BeanFieldWithAnnotation(beanField, annotation.value());
                result.add(m);
            }
        }


        Collections.sort(result, new Comparator<BeanFieldWithAnnotation>() {
            @Override
            public int compare(BeanFieldWithAnnotation o1, BeanFieldWithAnnotation o2) {
                return o1.getIndex() - o2.getIndex();
            }
        });
        return result;
    }


    public static BeanFieldWithAnnotation getFieldTypeByIndex(Integer fieldIndex, Class tClass) {
        Map<Integer, BeanFieldWithAnnotation> fieldIndexMap2 = fieldIndexMap.get(tClass);
        if (fieldIndexMap2 == null) {
            fieldIndexMap2 = getFieldIndexMap(tClass);
            fieldIndexMap.put(tClass, fieldIndexMap2);
        }
        return fieldIndexMap2.get(fieldIndex);
    }

    private static Map<Integer, BeanFieldWithAnnotation> getFieldIndexMap(Class tClass) {
        Map<Integer, BeanFieldWithAnnotation> result = new HashMap<>();
        List<BeanFieldWithAnnotation> x = getBeanSimpleSerializeFields1(tClass, SimpleSerializeField.class);
        for (BeanFieldWithAnnotation m : x) {
            result.put(m.getIndex(), m);
        }
        return result;
    }
}
