package br.com.jcv.security.guardian.infrastructure.redis;

import br.com.jcv.security.guardian.dto.RoleDTO;
import br.com.jcv.security.guardian.infrastructure.CacheProvider;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;

@Service(value = "redisService")
public class RedisService implements CacheProvider {

    @Autowired private Gson gson;

    @Autowired
    private @Qualifier("redisTemplate") RedisTemplate<String, Object> redisTemplate;
    @Override
    public <V> V getValue(String key) {
        return (V) redisTemplate.opsForValue().get(key);
    }

    @Override
    public <T> T getValue(String key, Class<T> type) {
        String cache = (String) redisTemplate.opsForValue().get(key);
        if(Objects.nonNull(cache)) return gson.fromJson(cache, type);
        return null;
    }

    @Override
    public <V> void setValue(String key, V value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public <V> void setValue(String key, V value, int seconds) {
        redisTemplate.opsForValue().set(key,value,Duration.ofSeconds(seconds));
    }
}