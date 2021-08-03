package ru.job4j.cache;

import org.junit.Test;

import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAdd() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        assertTrue(cache.add(base));
    }

    @Test
    public void whenAddSame() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        cache.add(base);
        assertFalse(cache.add(base));
    }

    @Test
    public void whenAddAnother() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        cache.add(base);
        base = new Base(1, 2);
        assertFalse(cache.add(base));
    }

    @Test(expected = OptimisticException.class)
    public void whenUpdateAnotherVersion() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        cache.add(base);
        base = new Base(1, 2);
        cache.update(base);
    }

    @Test()
    public void whenUpdate() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        cache.add(base);
        Base base2 = new Base(1, 1);
        base2.setName("name");
        assertTrue(cache.update(base2));
    }

    @Test
    public void delete() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        cache.add(base);
        cache.delete(base);
        assertTrue(cache.add(base));
    }
}