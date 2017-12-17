package com.mrspider.utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by mr on 2017/9/28.
 */
public class HttpUtils {

    private static HttpClient httpClient = new DefaultHttpClient();

    public static HttpClient getHttpClient() {
        return httpClient;
    }

    public static String getHtml(String personalUrl) {
        String res = "";
        HttpClient client = getHttpClient();
        //获取响应文件，即html，采用get方法获取响应数据
        HttpGet getMethod = new HttpGet(personalUrl);
        try {
            //执行get方法
            HttpResponse response = client.execute(getMethod);
            //获取响应状态码
            int StatusCode = response.getStatusLine().getStatusCode();
            //如果状态响应码为200，则获取html实体内容或者json文件
            if (StatusCode == 200) {
                res = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(res);
                EntityUtils.consume(response.getEntity());
            } else {
                //否则，消耗掉实体
                EntityUtils.consume(response.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return res;
    }


}
