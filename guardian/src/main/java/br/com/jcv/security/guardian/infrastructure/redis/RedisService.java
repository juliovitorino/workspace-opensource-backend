package br.com.jcv.security.guardian.infrastructure.redis;

import br.com.jcv.security.guardian.infrastructure.CacheProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service(value = "redisService")
public class RedisService implements CacheProvider {

    @Autowired
    private @Qualifier("redisTemplate") RedisTemplate<String, Object> redisTemplate;
    @Override
    public <V> V getValue(String key) {
        return (V) redisTemplate.opsForValue().get(key);
    }
    @Override
    public <V> void setValue(String key, V value) {
        redisTemplate.opsForValue().set(key, value);
    }
}