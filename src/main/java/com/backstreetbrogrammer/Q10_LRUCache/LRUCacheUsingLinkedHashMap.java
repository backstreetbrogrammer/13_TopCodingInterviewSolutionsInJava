package com.backstreetbrogrammer.Q10_LRUCache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCacheUsingLinkedHashMap<K, V> implements CustomHashTable<K, V> {
    private final LinkedHashMap<K, V> lruCache;

    public LRUCacheUsingLinkedHashMap(final int capacity) {
        this.lruCache = new LinkedHashMap<>(capacity, 1.0f, true) {
            @Override
            protected boolean removeEldestEntry(final Map.Entry<K, V> eldest) {
                return this.size() > capacity;
            }
        };
    }

    @Override
    public V get(final K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key must not be null");
        }

        return lruCache.get(key);
    }

    @Override
    public void put(final K key, final V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key must not be null");
        }
        if (value == null) {
            throw new IllegalArgumentException("Value must not be null");
        }

        // don't update if key is already present
        lruCache.putIfAbsent(key, value);
    }

    @Override
    public void remove(final K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key must not be null");
        }

        lruCache.remove(key);
    }
}
