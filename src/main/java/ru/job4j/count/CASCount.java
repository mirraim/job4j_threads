package ru.job4j.count;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        count.getAndUpdate(c -> c + 1);
    }

    public int get() {
        return count.get();
    }
}
