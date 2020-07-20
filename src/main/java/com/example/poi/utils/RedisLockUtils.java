package com.example.poi.utils;

import org.springframework.data.redis.core.RedisTemplate;

public class RedisLockUtils {
    private RedisTemplate<String,String> redisTemplate;
    //重试时间
    private static final int RETRY_TIME_MILLISECOND = 100;

    private static final String LOCK_SUFFIX = "redis_lock";

    private String key;
    //锁超时时间，防止当前线程加锁阻塞后面线程无法获取锁
    private int timeout = 1000;
    //加锁等待时间
    private int waitTime = 1000;
    //是否加锁
    private volatile boolean flag = false;

    public RedisLockUtils(RedisTemplate<String, String> redisTemplate, String key) {
        this.redisTemplate = redisTemplate;
        this.key = key + LOCK_SUFFIX;
    }

    public RedisLockUtils(RedisTemplate<String, String> redisTemplate, String key, int timeout) {
        this(redisTemplate,key);
        this.timeout = timeout;
    }

    public RedisLockUtils(RedisTemplate<String, String> redisTemplate, String key, int timeout, int waitTime) {
        this(redisTemplate,key,timeout);
        this.waitTime = waitTime;
    }

    public String getKey() {
        return key;
    }

    private String get(final String key){
        String s = redisTemplate.opsForValue().get(key);
        return s != null ? s : null;
    }

    private boolean setNX(final String key,final String value){
        Boolean b = redisTemplate.opsForValue().setIfAbsent(key, value);
        b = b != null ? b : false;
        return b;
    }

    private String getSet(final String key,final String value){
        String andSet = redisTemplate.opsForValue().getAndSet(key, value);
        return andSet != null ? andSet : null;
    }

    private synchronized boolean lock(){
        int wait = waitTime;
        while (wait >= 0){
            long expires = System.currentTimeMillis() + timeout +1;
            //锁到期时间
            String str = String.valueOf(expires);
            if(this.setNX(key,str)){
                flag = true;
                return flag;
            }
            String value = this.get(key);
            //判断是否过期 过期则重新设置并获取
            if(value != null && Long.parseLong(value) < System.currentTimeMillis()){
                //设置并返回旧值
                String old = this.getSet(key, str);
                //比较锁的时间 如果不一致可能其他锁已经修改
                if(old != null && old.equals(value)){
                    flag = true;
                    return flag;
                }
            }
            wait -= RETRY_TIME_MILLISECOND;
            //延时重试
            try {
                Thread.sleep(RETRY_TIME_MILLISECOND);
            }catch (InterruptedException e){
                 break;
            }
        }
        return false;
    }

    private synchronized void unlock(){
        if(flag){
            redisTemplate.delete(key);
            flag = false;
        }
    }
}
