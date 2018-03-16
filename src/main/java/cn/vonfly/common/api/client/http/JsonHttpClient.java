package cn.vonfly.common.api.client.http;

import cn.vonfly.common.api.client.sign.SignDataBackFill;
import cn.vonfly.common.api.client.sign.SignDto;
import cn.vonfly.common.api.client.utils.HttpClientFactory;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonHttpClient {
    protected static final Logger LOGGER = LoggerFactory.getLogger(JsonHttpClient.class);

    /**
     * @param req
     * @param url
     * @param <Req>
     * @return
     */
    public static <Req extends SignDto> String execute(Req req, String url,Gson reqGson) throws Exception {
        CloseableHttpResponse response = null;
        String requestJson = null;
        String result = null;
        try {
            //请求数据加密
            req.dataSign();
            HttpUriRequest httpRequest;
            if (req instanceof HttpPostExecute) {
                httpRequest = new HttpPost(url);
                requestJson = reqGson.toJson(req);
                ((HttpPost) httpRequest).setEntity(new StringEntity(requestJson, ContentType.create(ContentType.APPLICATION_FORM_URLENCODED.getMimeType(), Consts.UTF_8)));
            } else {
                httpRequest = new HttpGet(url);
            }
            response = HttpClientFactory.getHttpClient().execute(httpRequest);
            result = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
            String signData = null;
            if (req instanceof SignDataBackFill) {
                SignDataBackFill signDataBackFill = (SignDataBackFill) req;
                signData = signDataBackFill.getSignData();
            }
            LOGGER.info("http请求调用成功,url={},signData = {},requestJson={},result={}", url, null != signData ? signData : "未实现接口[SignDataBackFill],没有该数据", requestJson, result);
            return result;
        } catch (Exception e) {
            if (StringUtils.isBlank(requestJson)) {
                requestJson = reqGson.toJson(req);
            }
            LOGGER.error("请求处理异常url={},requestJson={},result={}", url, requestJson, result, e);
            throw e;
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (Exception e) {
                    LOGGER.error("CloseableHttpResponse 关闭异常", e);
                }
            }
        }
    }
}
