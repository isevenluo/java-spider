package org.pekxxoo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.util.Pool;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by chong on 2017/5/22.
 */
@Component
public class RedisUtil {

    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);


    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private ListOperations<String,Object> listOperations;


    /**
     * 将一个或多个值加入到key的表头
     *
     * @param key
     * @param values
     */
    public void lpush(String key, String... values) {
        this.listOperations.leftPushAll(key, values);
    }

    /**
     * 返回列表key中指定区间内的元素
     *
     * @param key
     * @param start
     * @param end
     */
    public List<Object> lrange(String key, int start, int end) {
        List<Object> result = null;
        result = listOperations.range(key, start, end);
        return result;
    }

    /**
     * 移除并返回列表 key 的尾元素
     *
     * @param key
     */
    public synchronized Object rpop(String key) {

        Object result = listOperations.rightPop(key);

        return result;
    }

    /**
     * 删除指定的key
     *
     * @param key
     */
    public void delete(String key) {
        redisTemplate.delete(key);

    }




}
