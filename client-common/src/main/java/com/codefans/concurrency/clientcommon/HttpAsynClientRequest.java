package com.codefans.concurrency.clientcommon;

import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author: ShengzhiCai
 * @date: 2018-11-23 14:36
 */
public class HttpAsynClientRequest {

    CloseableHttpAsyncClient httpclient;

    public HttpAsynClientRequest() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(3000)
                .setConnectTimeout(3000).build();
        httpclient = HttpAsyncClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();
        httpclient.start();
    }

    public HttpResponse get(String url) {

        HttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            Future<HttpResponse> future = httpclient.execute(httpGet, null);
            response = future.get();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public HttpResponse post(String url, Map<String, String> parmMap) {

        HttpResponse response = null;
        try {

            HttpPost httpPost = new HttpPost(url);

            if(parmMap != null && parmMap.size() > 0) {

                List<NameValuePair> formparams = new ArrayList<NameValuePair>();

                Iterator<String> keyIterator = parmMap.keySet().iterator();
                String key = "";
                String val = "";
                while(keyIterator.hasNext()) {
                    key = keyIterator.next();
                    val = parmMap.get(key);
                    formparams.add(new BasicNameValuePair(key, val));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
                httpPost.setEntity(entity);
            }

            Future<HttpResponse> future = httpclient.execute(httpPost, null);
            response = future.get();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public void close() {
        try {
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
