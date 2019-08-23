package com.codefans.concurrency.servercommon.service.impl;

import com.codefans.concurrency.servercommon.dao.UserDOMapper;
import com.codefans.concurrency.servercommon.domain.UserDO;
import com.codefans.concurrency.servercommon.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: codefans
 * @date: 2019-01-04 16:25:50
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private volatile static long addStartTime;
    private volatile static long minusStartTime;

    private static int totalAddRequests = 0;
    private static int totalMinusRequests = 0;
    private static int successAddRequests = 0;
    private static int successMinusRequests = 0;

    @Resource
    private UserDOMapper userDOMapper;

    @Override
    public UserDO queryUser(Long id) {
        return userDOMapper.queryUser(id);
    }

    @Override
    public int minusAmount(UserDO userDO) {

        if(minusStartTime == 0) {
            minusStartTime = System.currentTimeMillis();
        }

        totalMinusRequests++;
        int updateCount = userDOMapper.minusAmount(userDO);
        if(updateCount == 1) {
            successMinusRequests++;
        }
        log.info("totalMinusRequests=[" + totalMinusRequests + "], successMinusRequests=[" + successMinusRequests + "], total cost=[" + (System.currentTimeMillis() - minusStartTime)/1000 + "s]");
        return updateCount;
    }

    @Override
    public int addAmount(UserDO userDO) {

        if(addStartTime == 0) {
            addStartTime = System.currentTimeMillis();
        }

        totalAddRequests++;
        int updateCount = userDOMapper.addAmount(userDO);
        if(updateCount == 1) {
            successAddRequests++;
        }
        log.info("totalAddRequests=[" + totalAddRequests + "], successAddRequests=[" + successAddRequests + "], total cost=[" + (System.currentTimeMillis() - addStartTime)/1000 + "s]");
        return updateCount;
    }
}
