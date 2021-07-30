package ru.job4j.sync;

import java.util.concurrent.atomic.AtomicInteger;

public class User {
    private static AtomicInteger idCounter = new AtomicInteger();
    private final int id;
    private volatile int amount;

    public User() {
        this.id = idCounter.incrementAndGet();
    }

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
