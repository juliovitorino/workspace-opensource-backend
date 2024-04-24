package br.com.jcv.security.guardian.infrastructure;

import java.time.Duration;

public interface CacheProvider {

    <V> void setValue(String key, V value);
    <V> void setValue(String key, V value, int seconds);
    <V> V getValue(String key);
    <T> T getValue(String key, Class<T> type);
}
