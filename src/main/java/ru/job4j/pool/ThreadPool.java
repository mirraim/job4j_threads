package ru.job4j.pool;

import ru.job4j.synch.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();
    private final int size = Runtime.getRuntime().availableProcessors();

    private ThreadPool() {

    }

    public static ThreadPool init() {
        ThreadPool pool = new ThreadPool();
        for (int i = 0; i < pool.size; i++) {
            Thread thread = new Thread(() -> {
                try {
                    pool.tasks.poll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            });
            pool.threads.add(thread);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return pool;
    }

    public void work(Runnable job) {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }
}
