package cn.vonfly.common.api.client.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpClientFactory {
    private static volatile CloseableHttpClient httpClient = null;

    /**
     * 获取httpClient实例
     * @return
     */
    public static CloseableHttpClient getHttpClient(){
        if (null == httpClient){
            synchronized (HttpClientFactory.class){
                if (null == httpClient){
                    RequestConfig.Builder config = RequestConfig.custom();
                    config.setSocketTimeout(10000);
                    config.setConnectTimeout(8000);
                    config.setConnectionRequestTimeout(10000);
                    HttpClientBuilder builder = HttpClientBuilder.create();
                    builder.setDefaultRequestConfig(config.build());
                    PoolingHttpClientConnectionManager pools = new PoolingHttpClientConnectionManager();
                    pools.setDefaultMaxPerRoute(20);
                    pools.setMaxTotal(200);
                    builder.setConnectionManager(pools);
                    httpClient = builder.build();
                }
            }
        }
        return httpClient;
    }
}
