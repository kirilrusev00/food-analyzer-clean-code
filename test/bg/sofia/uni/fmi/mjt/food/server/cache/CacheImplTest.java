package bg.sofia.uni.fmi.mjt.food.server.cache;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class CacheImplTest {
    private Cache<Integer, String> cache;
    private static final int capacity = 10;

    @Before
    public void setUp() {
        cache = CacheFactory.getInstance(capacity);
    }

    @Test
    public void testGetWhenKeyIsIncluded() {
        final int testKey = 1;
        final String testValue = "value";
        cache.set(testKey, testValue);
        String assertMessage = "Not correct value";
        String actual = cache.get(testKey);
        assertEquals(assertMessage, testValue, actual);
    }

    @Test
    public void testGetWhenKeyIsNotIncluded() {
        final int testKey = 1;
        String assertMessage = "Key should not be in cache";
        String actual = cache.get(testKey);
        assertNull(assertMessage, actual);
    }

    @Test
    public void testSetWhenNotFull() {
        final int testKey = 1;
        String assertMessage = "Key should not be in cache";
        String actual = cache.get(testKey);
        assertNull(assertMessage, actual);
    }

    @Test
    public void testSetWhenFull() {
        for (int i = 1; i <= capacity; i++) {
            cache.set(i, "value " + i);
        }
        for (int i = 2; i <= capacity; i++) {
            cache.get(i);
        }
        //key 1 is LFU
        final int testKey = 11;
        final String testValue = "value " + testKey;
        cache.set(testKey, testValue);
        String assertMessage = "Key should not be in cache";
        String actual = cache.get(1);
        assertEquals(testValue, cache.get(testKey));
        assertNull(assertMessage, actual);
    }

    @Test
    public void testUsesCount() {
        for (int i = 1; i <= capacity; i++) {
            cache.set(i, "value " + i);
        }
        for (int i = 2; i <= capacity; i++) {
            cache.get(i);
        }
        final int testKey = 2;

        assertEquals(2, cache.getUsesCount(2));
    }
}
