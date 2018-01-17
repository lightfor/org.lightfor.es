package org.lightfor.es;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * http 客户端
 * @author Light
 * @date 2017/6/28.
 */
public enum HttpClient {

    /**
     * 实例
     */
    INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);

    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.SECONDS)
            .writeTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS).build();

    private static final int MAX_RETRY = 3;

    public static String get(String url) {
        Response response = null;
        for(int i = 0 ; i < MAX_RETRY ; i ++){
            try {
                Request request = new Request.Builder()
                        .url(url)
                        .get()
                        .build();
                response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                LOGGER.error("Http访问异常：{}", e.getLocalizedMessage());
            } finally {
                if(response != null){
                    try {
                        response.close();
                    } catch (Exception e){
                        LOGGER.error("响应关闭异常：{}", e.getLocalizedMessage());
                    }
                }
            }
        }
        return null;
    }

}
