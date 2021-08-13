package ru.job4j.pool;

import ru.job4j.synch.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(Integer.MAX_VALUE);
    private final int size = Runtime.getRuntime().availableProcessors();

    private ThreadPool() {

    }

    public static ThreadPool init() {
        ThreadPool pool = new ThreadPool();
        for (int i = 0; i < pool.size; i++) {
            Thread thread = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        pool.tasks.poll().run();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            pool.threads.add(thread);
            thread.start();
        }
        return pool;
    }

    public void work(Runnable job) {
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
        for (Thread thread : threads) {
            while (thread.getState() != Thread.State.TERMINATED) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public int jobSize() {
        return tasks.size();
    }
}
