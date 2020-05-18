package com.teamwork.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isConnected() {
        try {
            this.redisTemplate.opsForValue().get("test_connection");
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean expire(String key, long timeout) {
        try {
            if(timeout > 0) {
                this.redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        try {
            this.redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean set(String key, Object value, long time) {
        try {
            this.redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public Object get(String key) {
        return key == null ? null : this.redisTemplate.opsForValue().get(key);
    }

    public long incr(String key, long delta) {
        if(delta < 0) {
            throw new RuntimeException("delta must be greater then 0");
        }
        return this.redisTemplate.opsForValue().increment(key, delta);
    }

    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public long sSet(String key, Object... value) {
        try {
            return this.redisTemplate.opsForSet().add(key, value);
        } catch (Exception ex) {
            return 0;
        }
    }

    public long sSet(String key, long timeout, Object... value) {
        try {
            long count = this.redisTemplate.opsForSet().add(key, value);
            if(timeout > 0) {
                this.expire(key, timeout);
            }
            return count;
        } catch (Exception ex) {
            return 0;
        }
    }

    public Set<Object> sGet(String key) {
        try {
            return this.redisTemplate.opsForSet().members(key);
        } catch (Exception ex) {
            return null;
        }
    }

    public long lSet(String key, Object value) {
        try {
            return this.redisTemplate.opsForList().leftPush(key, value);
        } catch (Exception ex) {
            return 0;
        }
    }

    public List<Object> lGet(String key, long start, long end) {
        try {
            return this.redisTemplate.opsForList().range(key, start, end);
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Object> lGetAll(String key) {
        try {
            return this.redisTemplate.opsForList().range(key, 0, this.lSize(key) - 1);
        } catch (Exception ex) {
            return null;
        }
    }

    public long lRemove(String key, long count, Object value) {
        try {
            return this.redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception ex) {
            return 0;
        }
    }

    public long lSize(String key) {
        try {
            return this.redisTemplate.opsForList().size(key);
        } catch (Exception ex) {
            return 0;
        }
    }

    public void hset(String key, Map<Object, Object> fields) {
        try {
//            for (Map.Entry<String, Object> entry : fields.entrySet()) {
//                this.redisTemplate.opsForHash().put(key, entry.getKey(), entry.getValue());
//            }
            this.redisTemplate.opsForHash().putAll(key, fields);
        } catch (Exception e) {

        }
    }

    public Map<Object, Object> hget(String key) {
        try {
            return this.redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            return null;
        }
    }

    //region zset
    public Boolean zset(String key, Object value, double score) {
        return this.redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 获取所有zset集合元素
     */
    public Set<Object> zgetAll(String key) {
        Set<Object> set = this.redisTemplate.opsForZSet().range(key, 0, -1);
        return set;
    }

    /**
     * 获取指定范围的zset集合元素
     */
    public Set<Object> zgetAll(String key, long start, long stop) {
        Set<Object> set = this.redisTemplate.opsForZSet().range(key, start, stop);
        return set;
    }

    /**
     * 获取指定时间范围内的zset集合元素
     */
    public Set<Object> zgetByTime(String key, Date start, Date end) {
        long startTimestamp = DateUtil.convertDateToTimestamp(start);
        long endTimestamp = DateUtil.convertDateToTimestamp(end);
        Set<Object> set = this.redisTemplate.opsForZSet().range(key, startTimestamp, endTimestamp);
        return set;
    }
    //endregion

    public void delete(String key) {
        this.redisTemplate.delete(key);
    }

    public boolean lock(String key) {
//        this.redisTemplate.execute((RedisCallback) conn -> {
//            //过期时间
//            long expireAt = System.currentTimeMillis() + 300 + 1;
//            String lockKey = "lock_" + key;
//            Boolean accquired = conn.setNX(lockKey.getBytes(), String.valueOf(expireAt).getBytes());
//            if(accquired) { //设置key、value成功则表示成功获取到锁
//                return true;
//            } else {
//
//            }
//        });
        return false;
    }

    public void unlock(String key) {
        this.redisTemplate.delete(key);
    }
}
