package bg.sofia.uni.fmi.mjt.food.server.cache;

public class FoodInfoCache {

    private static InMemoryCache<String, String> cache = new InMemoryCache<>();
    private static InMemoryCache<String, String> gtinUpcCache = new InMemoryCache<>();

    public static String checkInCache(String searchInput) {
        return cache.get(searchInput);
    }

    public static String checkInGtinUpcCache(String searchInput) {
        return gtinUpcCache.get(searchInput);
    }

    public static void addToCache(String key, String values) {
        cache.add(key, values);
    }

    public static void addToGtinUpcCache(String key, String values) {
        gtinUpcCache.add(key, values);
    }
}
