package cn.vonfly.common.api.client.http;


import cn.vonfly.common.api.client.convert.json.gson.CustomGsonBuilder;
import cn.vonfly.common.api.client.dto.AbsReqSignDto;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.Serializable;

/**
 * 简单Json请求
 */
public class SimpleJsonHttpClient extends JsonHttpClient {
    protected static final Gson defReqGson = CustomGsonBuilder.reqGsonBuild();
    protected static final Gson defCommonGson = CustomGsonBuilder.commonGsonBuild();

    /**
     * 请求处理
     *
     * @param req
     * @param <Resp>
     * @param <Req>
     * @return
     */
    public static <Resp extends Serializable, Req extends AbsReqSignDto> Resp execute(Req req) {
        String result = null;
        try {
            result = JsonHttpClient.execute(req, req.buildUrl(), defReqGson);
            Object resp = defCommonGson.fromJson(result, req.getClass());
            if (resp instanceof RespDataPut){
                ((RespDataPut)resp).setResponseBody(result);
            }
            return (Resp) resp;
        } catch (Exception e) {
            String tip = "";
            if (e instanceof JsonSyntaxException) {
                tip = "返回结果反序列化异常,";
                LOGGER.error(tip + "result={},请求信息={},异常", result, defReqGson.toJson(req), e);
            }
            return (Resp) req.fail("500", tip + Throwables.getStackTraceAsString(e));
        }
    }
}
