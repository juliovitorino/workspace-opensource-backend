package br.com.jcv.security.guardian.infrastructure;

public interface CacheProvider {

    <V> void setValue(String key, V value);
    <V> V getValue(String key);
}
