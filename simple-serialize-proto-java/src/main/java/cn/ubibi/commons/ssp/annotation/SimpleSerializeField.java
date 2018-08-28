package cn.ubibi.commons.ssp.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SimpleSerializeField {
    int value();
}
