package com.codefans.concurrency.servertomcat.web.controller;

import com.alibaba.fastjson.JSON;
import com.codefans.concurrency.servercommon.data.Code;
import com.codefans.concurrency.servercommon.data.Message;
import com.codefans.concurrency.servercommon.data.Messages;
import com.codefans.concurrency.servercommon.util.ValidateUtil;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: codefans
 * @date: 2019-01-04 17:31:43
 */
@Controller
@RequestMapping("user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(method= {RequestMethod.GET,RequestMethod.POST}, value="/updateAmount/{version}/{appkey}")
    @ResponseBody
    public Message<String> callback(
            @PathVariable(value = "version") String version,
            @PathVariable(value = "appkey") String appkey,
            @RequestParam(value = "xmlParam") String xmlParam) {

        Message<String> message = null;
        try {
            if (!ValidateUtil.isNotEmpty(version,appkey,xmlParam)) {
                LOGGER.error("参数校验失败version={},appkey={},xmlParam={}", version, appkey, xmlParam);
                return Messages.failed(Code.PARAMATERSISNULL.getValue(), "参数校验失败");
            }

            LOGGER.info("callback params:[version={}, appkey={}, xmlParam={}]", version, appkey, xmlParam);

            Map<String, String> dataMap = new HashMap<String, String>();
            dataMap.put("version", version);
            dataMap.put("appkey", appkey);
            dataMap.put("xmlParam", xmlParam);
            dataMap.put("message", "welcome to concurrency testing program");

            message = Messages.success(JSON.toJSONString(dataMap));

            return message;
        } catch (Exception e) {
            LOGGER.info("回调系统异常.异常信息：{}", Throwables.getStackTraceAsString(e));
            return Messages.failed(Code.SYSTEMEXCEPTION.getValue(), "回调系统异常");
        }
    }

}
