package com.backstreetbrogrammer.Q10_LRUCache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LRUCacheUsingLinkedHashMapTest {

    private CustomHashTable<String, Integer> lruCache;

    @BeforeEach
    void setUp() {
        lruCache = new LRUCacheUsingLinkedHashMap<>(5);
    }

    @Test
    @DisplayName("When the key is null, then throw exception for get()")
    void whenKeyIsNull_thenThrowExceptionForGetMethod() {
        final Throwable exception = assertThrows(IllegalArgumentException.class, () -> lruCache.get(null));
        assertEquals(exception.getMessage(), "Key must not be null");
    }

    @Test
    @DisplayName("When the key is null, then throw exception for put()")
    void whenKeyIsNull_thenThrowExceptionForPutMethod() {
        final Throwable exception = assertThrows(IllegalArgumentException.class, () -> lruCache.put(null, 5));
        assertEquals(exception.getMessage(), "Key must not be null");
    }

    @Test
    @DisplayName("When the key is null, then throw exception for remove()")
    void whenKeyIsNull_thenThrowExceptionForRemoveMethod() {
        final Throwable exception = assertThrows(IllegalArgumentException.class, () -> lruCache.remove(null));
        assertEquals(exception.getMessage(), "Key must not be null");
    }

    @Test
    @DisplayName("When the Value is null, then throw exception for put()")
    void whenValueIsNull_thenThrowExceptionForPutMethod() {
        final Throwable exception = assertThrows(IllegalArgumentException.class, () -> lruCache.put("Rishi", null));
        assertEquals(exception.getMessage(), "Value must not be null");
    }


    @Test
    @DisplayName("Test LRU cache eviction")
    void testLRUCacheEviction() {
        lruCache = new LRUCacheUsingLinkedHashMap<>(2);

        lruCache.put("1", 1); // cache is {"1"=1}
        lruCache.put("2", 2); // cache is {"1"=1, "2"=2}

        // return 1
        assertEquals(1, lruCache.get("1"));

        // LRU key was "2", evicts key "2", cache is {"1"=1, "3"=3}
        lruCache.put("3", 3);
        assertNull(lruCache.get("2"));

        // LRU key was "1", evicts key "1", cache is {"4"=4, "3"=3}
        lruCache.put("4", 4);
        assertNull(lruCache.get("1"));

        // return 3
        assertEquals(3, lruCache.get("3"));

        // return 4
        assertEquals(4, lruCache.get("4"));

    }

}
