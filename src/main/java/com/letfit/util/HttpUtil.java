package com.letfit.util;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


/**
 * @author cjt
 */
public class HttpUtil {

    /**
     * 获得一个httpclient对象
     */
    private static final CloseableHttpClient httpclient = HttpClients.createDefault ();

    public static String sendGet (String url) {
        //生成一个get请求
        HttpGet httpget = new HttpGet (url);
        //请求返回结果
        CloseableHttpResponse response = null;
        try {
            //执行get请求并返回结果
            response = httpclient.execute (httpget);
        } catch (IOException e1) {
            e1.printStackTrace ();
        }
        String result = null;
        try {
            //处理结果
            HttpEntity entity = response.getEntity ();
            if (entity != null) {
                result = EntityUtils.toString (entity);
            }
        } catch (Exception e) {
            e.printStackTrace ();
        } finally {
            try {
                response.close ();
            } catch (IOException e) {
                e.printStackTrace ();
            }
        }
        return result;
    }


}
