package com.teamwork.service;

import com.teamwork.vo.ResourceVO;
import com.teamwork.vo.Result;
import com.teamwork.vo.paging.PageRequestVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestResourceService {

    @Autowired
    private ResourceService resourceService;

    @Test
    public void testInsert() {
        for (int i = 0; i < 105; i++) {
            ResourceVO item = new ResourceVO();
            item.setResourceName("资源" + i);
            this.resourceService.insert(item);
        }
    }

    @Test
    public void testGetAll() {
        PageRequestVO pageRequest = new PageRequestVO();
        pageRequest.setCurrentPage(4);
        pageRequest.setPageSize(10);
        Result result = this.resourceService.selectByPage(pageRequest);
        System.out.println(result);
    }
}
