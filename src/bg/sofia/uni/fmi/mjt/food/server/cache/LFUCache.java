package bg.sofia.uni.fmi.mjt.food.server.cache;

import java.util.*;

public class LFUCache<K, V> extends CacheImpl<K, V> {
    LFUCache() {
        super();
    }

    LFUCache(long capacity) {
        super(capacity);
    }

    @Override
    public void set(K key, V value) {
        if (!setWhenNotFull(key, value)) {
            K keyToRemove = getLFUElement();
            remove(keyToRemove);
            addToMaps(key, value);
        }
    }

    @Override
    public long getUsesCount(K key) {
        if (key != null && getUsesCountMap().containsKey(key)) {
            return getUsesCountMap().get(key);
        }
        return 0;
    }

    private HashMap<K, Long> sortByValue() {
        List<Map.Entry<K, Long>> list = new LinkedList<>(getUsesCountMap().entrySet());
        list.sort((o1, o2) -> (o1.getValue()).compareTo(o2.getValue()));
        HashMap<K, Long> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<K, Long> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    private K getLFUElement() {
        HashMap<K, Long> map = sortByValue();
        if (!map.isEmpty()) {
            Map.Entry<K, Long> entries = map.entrySet().iterator().next();
            return entries.getKey();
        }
        return null;
    }
}
