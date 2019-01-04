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

    @Resource
    private UserDOMapper userDOMapper;

    @Override
    public UserDO queryUser(Long id) {
        return userDOMapper.queryUser(id);
    }

    @Override
    public int updateAmount(UserDO userDO) {
        return userDOMapper.updateAmount(userDO);
    }
}
