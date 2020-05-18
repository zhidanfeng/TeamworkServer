package com.teamwork.service.impl;

import com.teamwork.service.TestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestServiceImpl implements TestService {

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void testA() {
        System.out.println("testA...");
        testB();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void testB() {
        System.out.println("testB...");
    }
}
