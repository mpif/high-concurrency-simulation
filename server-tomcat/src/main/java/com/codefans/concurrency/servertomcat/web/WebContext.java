package com.codefans.concurrency.servertomcat.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class WebContext {

    private static ThreadLocal<WebContext> tlv = new ThreadLocal<WebContext>();

    private HttpServletRequest request;

    private HttpServletResponse response;

    private Map<String, Object> map = new HashMap<String, Object>();

    public Object getObject(String key) {
        return map.get(key);
    }

    public void putObject(String key, Object object) {
        map.put(key, object);
    }

    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    protected WebContext() {
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    private WebContext(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public static WebContext getInstance() {
        return tlv.get();
    }

    public static void create(HttpServletRequest request, HttpServletResponse response) {
        WebContext wc = new WebContext(request, response);
        tlv.set(wc);
    }

    public static void clear() {
        tlv.set(null);
    }

}
