package cn.vonfly.common.api.client.convert.annotation;

import java.lang.annotation.*;

/**
 * 用于直接指定field的name(不适合一般转换策略的字段)
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SpecificFieldName {
    /**
     * 指定特定的名称
     * @return
     */
    String value() default "";

    /**
     * 是否不进行名称转换（即不使用NamingStrategy，使用字段名），默认false
     * @return
     */
    boolean noTranslate()default false;

}
