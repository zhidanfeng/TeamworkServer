package com.teamwork.aop;

import com.teamwork.annotation.RedisLock;
import com.teamwork.exceptions.TeamworkException;
import com.teamwork.inter.CacheKeyGenerator;
import com.teamwork.utils.RedisUtil;
import com.teamwork.utils.RedissonLockUtil;
import com.teamwork.vo.ResultCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.UUID;

@Aspect
@Configuration
public class LockMethodInterceptor {
    @Autowired
    public LockMethodInterceptor(RedisUtil redisUtil, CacheKeyGenerator cacheKeyGenerator) {
        this.redisUtil = redisUtil;
        this.cacheKeyGenerator = cacheKeyGenerator;
    }

    private final RedisUtil redisUtil;
    private final CacheKeyGenerator cacheKeyGenerator;


    @Around("execution(public * *(..)) && @annotation(com.teamwork.annotation.RedisLock)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        RedisLock lock = method.getAnnotation(RedisLock.class);
        if (StringUtils.isEmpty(lock.prefix())) {
            throw new RuntimeException("lock key don't null...");
        }
        final String lockKey = cacheKeyGenerator.getLockKey(pjp);
        String value = UUID.randomUUID().toString();
        try {
            // 假设上锁成功，但是设置过期时间失效，以后拿到的都是 false
            final boolean success = RedissonLockUtil.tryLock(lockKey, lock.expire());
            if (!success) {
                throw new TeamworkException(ResultCode.REPEAT_REQUEST);
            }
//            RedissonLockUtil.lock(lockKey, lock.expire());
            System.out.println("当前线程为：" + Thread.currentThread().getId());
            try {
                return pjp.proceed();
            } catch (Throwable throwable) {
                throw new RuntimeException("系统异常");
            }
        } finally {
            // TODO 如果演示的话需要注释该代码;实际应该放开
            RedissonLockUtil.unlock(lockKey);
        }
    }
}
