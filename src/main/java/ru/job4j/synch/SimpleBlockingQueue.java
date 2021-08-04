package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int limit;

    public SimpleBlockingQueue(int size) {
        this.limit = size;
    }

    public SimpleBlockingQueue() {
        this.limit = Integer.MAX_VALUE;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= limit) {
            wait();
        }
        queue.offer(value);
        System.out.println("Объект добавлен");
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T rsl = queue.poll();
        System.out.println("Объект удален");
        notifyAll();
        return rsl;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public synchronized int size() {
        return queue.size();
    }
}
