package bg.sofia.uni.fmi.mjt.food.server.cache;

public interface CacheFactory {

    static <K, V> Cache<K, V> getInstance(long capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        return new LFUCache<>(capacity);
    }

    static <K, V> Cache<K, V> getInstance() {
        return new LFUCache<>();
    }
}
