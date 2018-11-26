package com.codefans.concurrency.servertomcat.web.filter;

import com.alibaba.fastjson.JSON;
import com.codefans.concurrency.servertomcat.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class WebContextFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebContextFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Map<String, String[]> params =  req.getParameterMap();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String[]> entry : params.entrySet()) {
                String name = entry.getKey();
                String[] values = entry.getValue();
                if (values != null && values.length > 0) {
                    for (String value : values) {
                        LOGGER.info("name:" + name + ",value:" + value);
                        if (isXSS(value)) {
                            response.setContentType("application/json; charset=utf-8");
                            Map<String, Object> map = new HashMap<>();
                            map.put("result", 0);
                            map.put("code", 0);
                            map.put("message", "非法请求");
                            resp.getWriter().write(JSON.toJSONString(map));
                            return;
                        }
                    }
                }

            }
        }
        WebContext.create(req, resp);
        long startTime = System.currentTimeMillis();
        chain.doFilter(request, response);
        long endTime = System.currentTimeMillis();
        LOGGER.info("requestUrl:" + req.getRequestURI() + ",cost:" + (endTime - startTime));
    }

    private static final Pattern XSS_PATTERN = Pattern
            .compile("(.*\\s*)((<\\s*script\\s*)|(<\\s*embed\\s*)|(<\\s*style\\s*)|(<\\s*img\\s*)|(<\\s*image\\s*)|(<\\s*frame\\s*)|(<\\s*object\\s*)|(<\\s*iframe\\s*)|(<\\s*a\\s*)|(<\\s*frameset\\s*)|(<\\s*meta\\s*)|(<\\s*xml\\s*)|(<\\s*applet\\s*)|(\\s*onmouse\\s*)|(<\\s*link\\s*)|(\\s*onload\\s*)|(\\s*onblur\\s*)|(\\s*onchange\\s*)|(\\s*onclick\\s*)|(\\s*ondblclick\\s*)|(\\s*onfocus\\s*)|(\\s*onkey\\s*)|(\\s*onselect\\s*)|(\\s*alert\\s*\\())(.*\\s*)");

    /**
     * XSS校验
     *
     * @param content
     * @return
     */
    public static boolean isXSS(String content) {
        return XSS_PATTERN.matcher(content).matches();
    }

    @Override
    public void destroy() {
        WebContext.clear();
    }

    public static void main(String args[]) throws UnsupportedEncodingException {
        Map<String, Object> map= new HashMap<>();
        map.put("result", 0);
        map.put("code", 0);
        map.put("message", "非法请求".getBytes("UTF-8"));
        System.out.println(JSON.toJSONString(map));
    }
    
}
