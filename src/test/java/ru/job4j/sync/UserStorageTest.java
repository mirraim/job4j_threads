package ru.job4j.sync;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserStorageTest {
    @Test
    public void transferWhenNoMoney() {
        Store store = new ListStore();
        UserStorage storage = new UserStorage(store);
        User user = new User(1);
        storage.add(user);
        User otherUser = new User(2);
        storage.add(otherUser);
        assertFalse(storage.transfer(1, 2, 20));
    }

    @Test
    public void transferWhenNoUser() {
        Store store = new ListStore();
        UserStorage storage = new UserStorage(store);
        User user = new User(1);
        storage.add(user);
        user.setAmount(200);
        assertFalse(storage.transfer(1, 2, 20));
    }

    @Test
    public void transfer() {
        Store store = new ListStore();
        UserStorage storage = new UserStorage(store);
        User user = new User(1);
        user.setAmount(200);
        storage.add(user);
        User otherUser = new User(2);
        storage.add(otherUser);
        assertTrue(storage.transfer(1, 2, 20));
    }

    @Test
    public void sourceWhen20() {
        Store store = new ListStore();
        UserStorage storage = new UserStorage(store);
        User user = new User(1);
        user.setAmount(200);
        storage.add(user);
        User otherUser = new User(2);
        storage.add(otherUser);
        storage.transfer(1, 2, 20);
        assertEquals(user.getAmount(), 180);
    }
    @Test
    public void targetWhen20() {
        Store store = new ListStore();
        UserStorage storage = new UserStorage(store);
        User user = new User(1);
        user.setAmount(200);
        storage.add(user);
        User otherUser = new User(2);
        storage.add(otherUser);
        storage.transfer(1, 2, 20);
        assertEquals(otherUser.getAmount(), 20);
    }

    @Test
    public void targetWhenThreads() throws InterruptedException {
        Store store = new ListStore();
        UserStorage storage = new UserStorage(store);
        User user = new User(1);
        user.setAmount(200);
        storage.add(user);
        User otherUser = new User(2);
        storage.add(otherUser);
        Thread thread = new Thread(() -> storage.transfer(1, 2, 20));
        Thread another = new Thread(() -> storage.transfer(1, 2, 20));
        thread.start();
        another.start();
        thread.join();
        another.join();
        assertEquals(otherUser.getAmount(), 40);
    }

    @Test
    public void sourceWhenThreads() throws InterruptedException {
        Store store = new ListStore();
        UserStorage storage = new UserStorage(store);
        User user = new User(1);
        user.setAmount(200);
        storage.add(user);
        User otherUser = new User(2);
        storage.add(otherUser);
        Thread thread = new Thread(() -> storage.transfer(1, 2, 20));
        Thread another = new Thread(() -> storage.transfer(1, 2, 20));
        thread.start();
        another.start();
        thread.join();
        another.join();
        assertEquals(user.getAmount(), 160);
    }
}