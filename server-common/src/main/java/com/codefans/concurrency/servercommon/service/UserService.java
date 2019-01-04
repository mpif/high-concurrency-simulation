package com.codefans.concurrency.servercommon.service;

import com.codefans.concurrency.servercommon.domain.UserDO;

/**
 * @author: codefans
 * @date: 2019-01-04 16:25:43
 */
public interface UserService {

    /**
     * 查询用户
     * @param id
     * @return
     */
    public UserDO queryUser(Long id);

    /**
     * 更新金额
     * @param userDO
     * @return
     */
    public int updateAmount(UserDO userDO);

}
