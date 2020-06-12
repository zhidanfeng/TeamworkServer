package com.teamwork.utils;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * 1、在不设置锁过期时间时，默认是取的lockWatchdogTimeout的值，默认30秒
 *
 * 2、
 * 在不适用Redisson时，如果业务执行时间超过redis锁的时间，则会出现A超时，然后B获取到锁，之后A执行完毕又去释放锁
 * 导致A释放了B设置的锁，针对这种情况，在设置Redis锁的时候，一般需要将持有锁的对象的id，比如客户端id设置成key的value
 * 在释放锁时，需要比对这个id，防止释放了别人持有的锁
 *
 * 而Redisson则避免了这种情况，它是将value设置成了UUID:threadId，从而保证了唯一性，这样只有拥有锁的进程才能去释放锁
 * 其他进程解锁则会抛出IllegalMonitorStateException错误
 * 参考：https://github.com/redisson/redisson/wiki/8.-%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81%E5%92%8C%E5%90%8C%E6%AD%A5%E5%99%A8
 *
 * 3、在未手动设置锁过期时间的情况下，Redisson在获取锁之后，会开启一个watchDog线程，每次调度的时间间隔是LockLeaseTime / 3L(RedissonLock类的renewExpiration方法)
 */
public class RedissonLockUtil {

    private static RedissonClient redissonClient;

    public static void setRedissonClient(RedissonClient redissonClient) {
        RedissonLockUtil.redissonClient = redissonClient;
    }

    public static RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    public static RLock lock(String lockKey, long leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return lock;
    }

    public static boolean tryLock(String lockKey, long waitTime, long leaseTime, TimeUnit unit) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    public static void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }
}
