package com.teamwork;

import com.teamwork.service.CommonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TeamworkGeneralApplicationTests {

    @Autowired
    private CommonService commonService;

    @Test
    void contextLoads() {
        System.out.println("test");
        this.commonService.getViewId();
    }

}
