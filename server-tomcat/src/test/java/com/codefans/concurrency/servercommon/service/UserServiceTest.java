package com.codefans.concurrency.servercommon.service;

import com.codefans.concurrency.servercommon.domain.UserDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author:
 * @date: 2019-01-07 18:29:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-config.xml"})
@TestPropertySource(properties={"env=dev"})
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void addAmountTest() {

        UserDO userDO = new UserDO();
        userDO.setId(1L);
        userDO.setAmount(1L);
        int updateCount = userService.addAmount(userDO);
        System.out.println("updateCount=" + updateCount);


    }

    @Test
    public void minusAmountTest() {

        UserDO userDO = new UserDO();
        userDO.setId(1L);
        userDO.setAmount(1L);
        int updateCount = userService.minusAmount(userDO);
        System.out.println("updateCount=" + updateCount);


    }

}
