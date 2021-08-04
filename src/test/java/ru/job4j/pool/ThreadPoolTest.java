package ru.job4j.pool;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class ThreadPoolTest {

    @Test
    public void work() {
        ThreadPool pool = ThreadPool.init();
        AtomicInteger count = new AtomicInteger(0);
        pool.work(new Thread(count::incrementAndGet));
        pool.work(new Thread(count::incrementAndGet));
        pool.work(new Thread(count::incrementAndGet));
        pool.shutdown();
        assertEquals(count.get(), 3);
    }


    @Test
    public void workWhenMany() {
        ThreadPool pool = ThreadPool.init();
        AtomicInteger count = new AtomicInteger(0);
        for (int i = 0; i < 364; i++) {
            pool.work(new Thread(count::incrementAndGet));
        }
        pool.shutdown();
        assertEquals(count.get(), 364 - pool.jobSize());
    }
}