package com.codefans.concurrency.clientcommon;

import com.codefans.concurrency.clientcommon.util.StringUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.URIUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @author: codefans
 * @date: 2019-08-13 10:11
 *
 * Java用HttpClient3发送Get和Post请求(增强)
 * https://happyqing.iteye.com/blog/2204247
 *
 * 高并发场景下的httpClient优化使用
 * https://www.cnblogs.com/davidwang456/articles/8526964.html
 *
 */
public class HttpSyncClientRequest {

    /**
     * 执行一个HTTP GET请求，返回请求响应的HTML
     *
     * @param url                 请求的URL地址
     * @param queryString 请求的查询参数,可以为null
     * @param charset         字符集
     * @param pretty            是否美化
     * @return 返回请求响应的HTML
     */
    public String doGet(String url, String queryString, String charset, boolean pretty) {
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        try {
            if (StringUtils.isNotBlank(queryString))
                //对get请求参数做了http请求默认编码，好像没有任何问题，汉字编码后，就成为%式样的字符串
                method.setQueryString(URIUtil.encodeQuery(queryString));
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (pretty)
                        response.append(line).append(System.getProperty("line.separator"));
                    else
                        response.append(line);
                }
                reader.close();
            }
        } catch (URIException e) {
            System.out.println("执行HTTP Get请求时，编码查询字符串“" + queryString + "”发生异常！" + e);
        } catch (IOException e) {
            System.out.println("执行HTTP Get请求" + url + "时，发生异常！" + e);
        } finally {
            method.releaseConnection();
        }
        return response.toString();
    }

    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     *
     * @param url         请求的URL地址
     * @param params    请求的查询参数,可以为null
     * @param charset 字符集
     * @param pretty    是否美化
     * @return 返回请求响应的HTML
     */
    public String doPost(String url, Map<String, String> params, String charset, boolean pretty) {
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        //设置参数的字符集
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,charset);
        //设置Http Post数据
        if (params != null) {
            //HttpMethodParams p = new HttpMethodParams();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                //p.setParameter(entry.getKey(), entry.getValue());
                method.setParameter(entry.getKey(), entry.getValue());
            }
            //method.setParams(p);
        }
        try {
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (pretty)
                        response.append(line).append(System.getProperty("line.separator"));
                    else
                        response.append(line);
                }
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("执行HTTP Post请求" + url + "时，发生异常！" + e);
        } finally {
            method.releaseConnection();
        }
        return response.toString();
    }

}
