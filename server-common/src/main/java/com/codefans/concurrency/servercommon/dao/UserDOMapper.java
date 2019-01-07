package com.codefans.concurrency.servercommon.dao;

import com.codefans.concurrency.servercommon.domain.UserDO;

public interface UserDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    int insert(UserDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    int insertSelective(UserDO record);

    /**
     * 查询用户
     * @param id
     * @return
     */
    UserDO queryUser(Long id);

    /**
     * 减少金额
     * @param userDO
     * @return
     */
    int minusAmount(UserDO userDO);

    /**
     * 增加金额
     * @param userDO
     * @return
     */
    int addAmount(UserDO userDO);

}