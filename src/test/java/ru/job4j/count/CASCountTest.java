package ru.job4j.count;

import org.junit.Test;

import java.io.InputStream;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class CASCountTest {

    @Test
    public void whenOne() {
        CASCount counter = new CASCount();
        counter.increment();
        assertEquals(counter.get(), 1);
    }

    @Test
    public void whenNull() {
        CASCount counter = new CASCount();
        assertEquals(counter.get(), 0);
    }

    @Test
    public void whenTwo() {
        CASCount counter = new CASCount();
        counter.increment();
        counter.increment();
        assertEquals(counter.get(), 2);
    }

    @Test
    public void whenThreads() throws InterruptedException {
        CASCount counter = new CASCount();
        Thread first = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                counter.increment();
            }
        });
        first.start();
        Thread second = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                counter.increment();
            }
        });
        second.start();
        first.join();
        second.join();
        assertEquals(counter.get(), 6);
    }

    @Test
    public void when350() throws InterruptedException {
        CASCount counter = new CASCount();
        Thread first = new Thread(() -> {
            for (int i = 0; i < 120; i++) {
                counter.increment();
            }
        });
        first.start();
        Thread second = new Thread(() -> {
            for (int i = 0; i < 230; i++) {
                counter.increment();
            }
        });
        second.start();
        first.join();
        second.join();
        assertEquals(counter.get(), 350);
    }
}