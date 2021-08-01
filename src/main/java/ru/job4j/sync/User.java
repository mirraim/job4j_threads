package ru.job4j.sync;

import java.util.concurrent.atomic.AtomicInteger;

public class User {
    private final int id;
    private int amount;

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
