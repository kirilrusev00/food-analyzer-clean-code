package bg.sofia.uni.fmi.mjt.food.server.cache;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class InMemoryCacheTest {
    private InMemoryCache<Integer, String> cache;

    @Before
    public void setUp() {
        cache = new InMemoryCache<>();
    }

    @Test
    public void testGetWhenKeyIsIncluded() {
        final int testKey = 1;
        final String testValue = "value";

        cache.add(testKey, testValue);

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
    public void testAddWhenKeyIsNotNullAndValueIsNotNull() {
        final int testKey = 1;
        final String testValue = "value";

        cache.add(testKey, testValue);

        String assertMessage = "Key should be in cache";
        String actual = cache.get(testKey);

        assertEquals(assertMessage, testValue, actual);
    }

    @Test
    public void testAddWhenKeyIsIncludedAndValueIsNull() {
        final int testKey = 1;
        final String testValue = "value";
        final String testValue2 = null;

        cache.add(testKey, testValue);
        cache.add(testKey, testValue2);

        String assertMessage = "Key should not be in cache";
        String actual = cache.get(testKey);

        assertNull(assertMessage, actual);
    }

}
