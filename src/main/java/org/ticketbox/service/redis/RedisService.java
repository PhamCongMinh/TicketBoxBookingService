package org.ticketbox.service.redis;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
//@AllArgsConstructor
//@NoArgsConstructor
public class RedisService {
    private RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // Basic key-value
    public void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setValue(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // Hash
    public void setHashValue(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public Object getHashValue(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    public Map<Object, Object> getHashEntries(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    // Lua script
    public <T> T executeLuaScript(String luaScript, Class<T> resultType, List<String> keys, Object... args) {
        try {
            DefaultRedisScript<T> redisScript = new DefaultRedisScript<>(luaScript, resultType);
            return redisTemplate.execute(redisScript, keys, args);
        } catch (RedisSystemException e) {
            System.out.println("Lua script execution failed: " + e.getMessage());
            System.out.println("Script: " + luaScript);
            System.out.println("Keys: " + keys);
            System.out.println("Args: " + Arrays.toString(args));
            throw e;
        }
    }
}

