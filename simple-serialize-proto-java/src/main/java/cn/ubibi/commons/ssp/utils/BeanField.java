package cn.ubibi.commons.ssp.utils;


import java.lang.reflect.Field;

public class BeanField {


    private Field field;
    private String fieldName;
    private String fieldNameUnderline;

    public BeanField(Field field) {
        this.field = field;
        setAccessible();

        String filedName = field.getName();
        String underlineFiledName = StringUtils.camel2Underline(field.getName());
        this.fieldName = filedName;
        if (filedName.equals(underlineFiledName)) {
            this.fieldNameUnderline = filedName;//为了加快下次比较速度。
        } else {
            this.fieldNameUnderline = underlineFiledName;
        }
    }


    public Field getField() {
        return field;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldNameUnderline() {
        return fieldNameUnderline;
    }


    public void setBeanValue(Object bean, Object value) throws IllegalAccessException {
        setAccessible();
        field.set(bean, value);
    }


    public Object getBeanValue(Object bean) throws IllegalAccessException {
        setAccessible();
        return field.get(bean);
    }

    private void setAccessible() {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
    }


    /**
     * 会自动转换
     *
     * @param bean
     * @param value
     * @throws Exception
     */
    public void setBeanValue_autoConvert(Object bean, Object value) throws Exception {
        setAccessible();
        value = valueOf(value);
        field.set(bean, value);
    }


    public Object valueOf(Object value) throws Exception {
        Field field = this.field;
        Class<?> targetType = field.getType();

        if (value == null) {
            if (CastBasicTypeUtils.isBasicType(targetType)) {
                return CastBasicTypeUtils.toBasicTypeOf(0, targetType);
            }
            return null;
        }


        if (targetType == value.getClass()) {
            return value;
        }


        //2. 对于字符串数据
        if (value instanceof String || value instanceof StringBuilder || value instanceof StringBuffer) {

            String stringValue = value.toString();
            value = stringValue;
        }

        //3. 简单数据类型转换
        return CastBasicTypeUtils.toBasicTypeOf(value, targetType);
    }
}

