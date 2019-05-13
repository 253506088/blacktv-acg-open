package blacktv.tvacg.database.service;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * 连接Redis操作的工具类
 */
@Service
@Log4j
public class RedisUtil {
    @Value("${tvacg.redis.port}")
    private int port;
    @Value("${tvacg.redis.password}")
    private String password;
    private Jedis jedis;

    /**
     * 单例模式
     */
    private void ini() {
        //没连接就连接一哈
        try {
            if (jedis == null)
                jedis = new Jedis("localhost", port);//不写端口默认6379
            if (password != null && password.length() > 2)
                jedis.auth(password);
            log.info("测试与redis的连接:" + jedis.ping());
        } catch (Exception e) {
            log.info("与redis连接失败或关闭，错误信息: " + e.toString());
            jedis = new Jedis("localhost", port);//重新连接
        }
    }

    /**
     * 保存field与value这个键值对到指定的hash类型的key
     *
     * @param key
     * @param field
     * @param value
     */
    public void hashSet(String key, String field, String value) {
        ini();
        jedis.hset(key, field, value);
    }

    /**
     * 获取指定hash类型的key里field对应的value
     *
     * @param field
     * @param key
     * @return
     */
    public String hashGet(String key, String field) {
        ini();
        return jedis.hget(key, field);
    }

    /**
     * 保存一个String的键值对,并设置有效时长（单位秒）,如果传入的seconds等于小0则不限制时长
     *
     * @param key
     * @param string
     * @param seconds
     * @return
     */
    public void stringSet(String key, String string, int seconds) {
        ini();
        jedis.set(key, string);
        if (seconds > 0)
            jedis.expire(key, seconds);
    }

    /**
     * 根据key获取保存的字符串键值对
     *
     * @param key
     * @return
     */
    public String stringGet(String key) {
        ini();
        return jedis.get(key);
    }

    /**
     * 删除指定的key和对应的值
     *
     * @param key
     * @return
     */
    public boolean delete(String key) {
        ini();
        return jedis.del(key) >= 1;//删除成功返回1，失败返回0
    }

    /**
     * 验证key是否存在，存在返回返回true
     *
     * @param key
     * @return
     */
    public boolean exists(String key) {
        ini();
        return jedis.exists(key);
    }
}