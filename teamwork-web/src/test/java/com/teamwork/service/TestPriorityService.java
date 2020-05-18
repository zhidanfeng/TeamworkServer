package com.teamwork.service;

import com.teamwork.vo.PriorityVO;
import com.teamwork.vo.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestPriorityService {

    @Autowired
    private PriorityService priorityService;

    @Test
    public void testInsert() {
        PriorityVO entity = new PriorityVO();
        entity.setPriorityName("非常重要");
        entity.setPrioritySeq(4);
        entity.setPriorityColor("#E62412");
        this.priorityService.insert(entity);

        entity = new PriorityVO();
        entity.setPriorityName("紧急");
        entity.setPrioritySeq(3);
        entity.setPriorityColor("#FA8C15");
        this.priorityService.insert(entity);

        entity = new PriorityVO();
        entity.setPriorityName("普通");
        entity.setPrioritySeq(2);
        entity.setPriorityColor("#1B9AEE");
        this.priorityService.insert(entity);

        entity = new PriorityVO();
        entity.setPriorityName("较低");
        entity.setPrioritySeq(1);
        entity.setPriorityColor("#8C8C8C");
        this.priorityService.insert(entity);
    }

    @Test
    public void testGetAll() {
        Result result = this.priorityService.selectAll();
        System.out.println(result.getData());
    }
}
