package ru.job4j.sync;

import org.junit.Test;

import static org.junit.Assert.*;

public class ListStoreTest {
    @Test
    public void add() {
        Store store = new ListStore();
        UserStorage storage = new UserStorage(store);
        User user = new User(1);
        assertTrue(storage.add(user));
    }

    @Test
    public void update() {
        Store store = new ListStore();
        UserStorage storage = new UserStorage(store);
        User user = new User(1);
        storage.add(user);
        User otherUser = new User(1);
        storage.update(otherUser);
        assertEquals(store.findById(1), otherUser);
    }

    @Test
    public void updateWhenNoUser() {
        Store store = new ListStore();
        User user = new User(1);
        store.add(user);
        User otherUser = new User(2);
        assertFalse(store.update(otherUser));
    }

    @Test
    public void delete() {
        Store store = new ListStore();
        User user = new User(1);
        store.add(user);
        store.delete(user);
        assertNull(store.findById(1));
    }

    @Test
    public void deleteWhenNoUser() {
        Store store = new ListStore();
        User user = new User(1);
        store.add(user);
        User otherUser = new User(2);
        assertFalse(store.delete(otherUser));
    }

    @Test
    public void whenFind() {
        Store store = new ListStore();
        User user = new User(1);
        store.add(user);
        assertEquals(store.findById(1), user);
    }

    @Test
    public void whenNoFind() {
        Store store = new ListStore();
        User user = new User(1);
        store.add(user);
        assertNull(store.findById(2));
    }
}