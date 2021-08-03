package ru.job4j.synch;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {
    @Test
    public void whenEmpty() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread produser = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                queue.offer(i);
            }
        });
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    queue.poll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        produser.start();
        consumer.start();
        produser.join();
        consumer.join();
        assertTrue(queue.isEmpty());
    }

    @Test
    public void whenNotEmpty() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread produser = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                queue.offer(i);
            }
        });
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 9; i++) {
                try {
                    queue.poll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        produser.start();
        consumer.start();
        produser.join();
        consumer.join();
        assertFalse(queue.isEmpty());
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(
                () -> IntStream.range(0, 5).forEach(
                        queue::offer
                )
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertEquals(buffer, Arrays.asList(0, 1, 2, 3, 4));
    }

    @Test
    public void whenFetchAllThenGetItAll() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(
                () -> Arrays.stream(new int[]{3, 8, 16, 25}).forEach(
                        queue::offer
                )
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertEquals(buffer, Arrays.asList(3, 8, 16, 25));
    }
}