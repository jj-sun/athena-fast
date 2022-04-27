package com.athena.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Mr.sun
 * @date 2022/1/19 17:36
 * @description 字典注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dict {
    /**
     * 数据code
     * @return String
     */
    String dicCode();

    /**
     * 数据Text
     * @return String
     */
    String dicText() default "";

    /**
     * 数据字典表
     * @return String
     */
    String dictTable() default "";
}
