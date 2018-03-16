package cn.vonfly.common.api.client.sign.annotation;

import java.lang.annotation.*;

/**
 * 指定参与签名时忽略的字段
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SignIgnore {
    /**
     * 排除的目标类(被排除的类不会在计算签名时忽略该字段)
     * @return
     */
    Class<?>[] exclusions() default {};
}
