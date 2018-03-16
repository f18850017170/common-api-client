package cn.vonfly.common.api.client.convert.json.gson.strategy;


import cn.vonfly.common.api.client.convert.annotation.ReqIgnore;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * 根据自定义注解忽略请求字段
 * gosn序列化策略实现
 */
public class ReqFieldExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        ReqIgnore reqIgnore = f.getAnnotation(ReqIgnore.class);
        return null != reqIgnore;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
