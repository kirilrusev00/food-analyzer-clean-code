package bg.sofia.uni.fmi.mjt.food.server.cache;

import java.util.*;

public abstract class CacheImpl<K, V> implements Cache<K, V> {
    private static final long MAX_CAPACITY = 10000L;
    private Map<K, V> cache;
    private Map<K, Long> usesCount;
    private long capacity;

    CacheImpl() {
        this.cache = new LinkedHashMap<>();
        this.usesCount = new LinkedHashMap<>();
        this.capacity = MAX_CAPACITY;
    }

    CacheImpl(long capacity) {
        this();
        this.capacity = capacity;
    }

    @Override
    public V get(K key) {
        if (key != null && this.cache.containsKey(key)) {
            long oldCount = this.usesCount.get(key);
            usesCount.replace(key, oldCount + 1L);
            return cache.get(key);
        }
        return null;
    }

    boolean setWhenNotFull(K key, V value) {
        if (key == null || value == null) {
            return true;
        }
        if (this.cache.containsKey(key)) {
            cache.replace(key, value);
            long oldCount = this.usesCount.get(key);
            usesCount.replace(key, oldCount + 1L);
            return true;
        }
        if (cache.size() < capacity) {
            cache.put(key, value);
            usesCount.put(key, 1L);
            return true;
        }
        return false;
    }

    @Override
    public abstract void set(K key, V value);

    @Override
    public boolean remove(K key) {
        if (key != null && this.cache.containsKey(key)) {
            cache.remove(key);
            usesCount.remove(key);
            return true;
        }
        return false;
    }

    @Override
    public long size() {
        return cache.size();
    }

    @Override
    public void clear() {
        cache.clear();
        usesCount.clear();
    }

    void addToMaps(K key, V value) {
        cache.put(key, value);
        usesCount.put(key, 1L);
    }

    public Map<K, V> getCache() {
        return cache;
    }

    Map<K, Long> getUsesCountMap() {
        return usesCount;
    }

    @Override
    public abstract long getUsesCount(K key);
}
