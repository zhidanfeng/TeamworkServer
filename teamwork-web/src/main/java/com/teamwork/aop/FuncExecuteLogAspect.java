package com.teamwork.aop;

import com.teamwork.utils.ThreadLocalUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FuncExecuteLogAspect {

    private final static Logger logger = LoggerFactory.getLogger(FuncExecuteLogAspect.class);

    @Pointcut("execution(* com.teamwork.controller.*Controller.*(..))")
    public void controllerLog() {

    }

    @Pointcut("execution(* com.teamwork.service.*Service.*(..))")
    public void serviceLog() {

    }

    @Before("controllerLog()")
    public void logBeforeController(JoinPoint joinPoint) {
        logger.info("开始执行" + joinPoint.getSignature().getName() + "，logId=" + ThreadLocalUtil.getLogId());
    }

    @Before("serviceLog()")
    public void logBeforeService(JoinPoint joinPoint) {
        logger.info("开始执行" + joinPoint.getSignature().getName() + "，logId=" + ThreadLocalUtil.getLogId());
    }

    @After("controllerLog()")
    public void logAfterController() {
        System.out.println("logAfterController...");
    }

    /**
     * 在使用arround时，对应的被代理方法如果有返回值，则这里的arround也需要有返回值
     * 否则如果返回void会导致Controller、Service层有return结果，但是客户端却收到了null的情况
     */
    @Around("controllerLog() || serviceLog()")
    public Object arroud(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("[环绕前：]");
        // 执行目标方法
        return pjp.proceed();
    }
}
