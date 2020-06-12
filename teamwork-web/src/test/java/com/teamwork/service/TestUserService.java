package com.teamwork.service;

import com.teamwork.vo.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestUserService {

    @Autowired
    private UserService userService;

    @Test
    public void testInsertBatch() {
        List<UserInfo> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserInfo item = new UserInfo();
            item.setUserName("zhi" + i);
            item.setPassword("123456");
            items.add(item);
        }
        this.userService.insertBatch(items);
    }

    @Test
    public void testGetAll() {

    }
}
