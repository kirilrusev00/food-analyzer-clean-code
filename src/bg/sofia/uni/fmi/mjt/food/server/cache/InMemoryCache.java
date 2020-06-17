package bg.sofia.uni.fmi.mjt.food.server.cache;

import java.lang.ref.SoftReference;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCache<K, V> {

    private static final int CLEAN_UP_PERIOD_IN_SEC = 5;
    private static final long PERIOD_TO_STAY_IN_CACHE_IN_MILLIS = 86400000L; // 1 day

    private final ConcurrentHashMap<K, SoftReference<CacheObject<V>>> cache = new ConcurrentHashMap<>();

    InMemoryCache() {
        Thread cleanerThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(CLEAN_UP_PERIOD_IN_SEC * 1000);
                    cache.entrySet()
                            .removeIf(entry -> Optional.ofNullable(entry.getValue())
                                    .map(SoftReference::get)
                                    .map(CacheObject::isExpired)
                                    .orElse(false));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        cleanerThread.setDaemon(true);
        cleanerThread.start();
    }

    void add(K key, V value) {
        if (key == null) {
            return;
        }
        if (value == null) {
            cache.remove(key);
        } else {
            long expiryTime = System.currentTimeMillis() + PERIOD_TO_STAY_IN_CACHE_IN_MILLIS;
            cache.put(key, new SoftReference<>(new CacheObject<>(value, expiryTime)));
        }
    }

    V get(K key) {
        return Optional.ofNullable(cache.get(key))
                .map(SoftReference::get)
                .filter(cacheObject -> !cacheObject.isExpired())
                .map(CacheObject::getValue)
                .orElse(null);
    }

    private static class CacheObject<V> {

        private V value;
        private long expiryTime;

        CacheObject(V value, long expiryTime) {
            this.value = value;
            this.expiryTime = expiryTime;
        }

        V getValue() {
            return value;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }
}