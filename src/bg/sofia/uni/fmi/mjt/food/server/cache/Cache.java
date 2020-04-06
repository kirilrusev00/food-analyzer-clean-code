package bg.sofia.uni.fmi.mjt.food.server.cache;

public interface Cache<K, V> {
    V get(K key);

    void set(K key, V value);

    boolean remove(K key);

    long size();

    void clear();

    long getUsesCount(K key);
}
