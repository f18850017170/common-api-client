package cn.vonfly.common.api.client.convert.json.gson;

import cn.vonfly.common.api.client.convert.json.gson.strategy.DefFieldNamingStrategy;
import cn.vonfly.common.api.client.convert.json.gson.strategy.ReqFieldExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CustomGsonBuilder {
    private volatile static Gson reqGson = null;
    private volatile static Gson gson = null;

    /**
     * 签名使用gson自定义构建
     * 使用CustomFieldNamingStrategy
     *
     * @return
     */
    public static Gson commonGsonBuild(DefFieldNamingStrategy defFieldNamingStrategy) {
        return new GsonBuilder()
                .setFieldNamingStrategy(defFieldNamingStrategy)
                .create();
    }
    /**
     * 签名使用gson自定义构建
     * 使用CustomFieldNamingStrategy
     *
     * @return
     */
    public static Gson commonGsonBuild() {
        if (null == gson) {
            synchronized (CustomGsonBuilder.class) {
                if (null == gson) {
                    gson = new GsonBuilder()
                            .setFieldNamingStrategy(new DefFieldNamingStrategy())
                            .create();
                }
            }
        }
        return gson;
    }

    /**
     * 根据自定义的名称转换策略 构建gosn
     * @param defFieldNamingStrategy
     * @return
     */
    public static Gson reqGsonBuild(DefFieldNamingStrategy defFieldNamingStrategy) {
        return new GsonBuilder()
                .setFieldNamingStrategy(defFieldNamingStrategy)
                .setExclusionStrategies(new ReqFieldExclusionStrategy())
                .create();
    }
    /**
     * 请求使用gson自定义构建
     * 使用CustomFieldNamingStrategy
     * 使用ReqFieldExclusionStrategy
     * @return
     */
    public static Gson reqGsonBuild() {
        if (null == reqGson) {
            synchronized (CustomGsonBuilder.class) {
                if (null == reqGson) {
                    reqGson = new GsonBuilder()
                            .setFieldNamingStrategy(new DefFieldNamingStrategy())
                            .setExclusionStrategies(new ReqFieldExclusionStrategy())
                            .create();
                }
            }
        }
        return reqGson;
    }
}
