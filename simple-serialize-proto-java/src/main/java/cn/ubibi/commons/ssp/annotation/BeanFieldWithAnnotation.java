package cn.ubibi.commons.ssp.annotation;

import cn.ubibi.commons.ssp.utils.BeanField;

import java.lang.reflect.Field;

public class BeanFieldWithAnnotation {
    private final Class<?> type;
    private BeanField beanField;
    private int index;
    private Field field;

    public BeanFieldWithAnnotation(BeanField beanField, int index) {
        this.beanField = beanField;
        this.index = index;
        this.field = beanField.getField();
        this.type = beanField.getField().getType();
    }


    public Class<?> getType() {
        return type;
    }

    public BeanField getBeanField() {
        return beanField;
    }

    public void setBeanField(BeanField beanField) {
        this.beanField = beanField;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setBeanValue(Object objectInstance, Object value) throws Exception {
        this.beanField.setBeanValue(objectInstance, value);
    }

}