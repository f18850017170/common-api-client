package cn.vonfly.common.api.client.convert.annotation;

import java.lang.annotation.*;

/**
 * 构建请求时忽略的字段
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ReqIgnore {
}
