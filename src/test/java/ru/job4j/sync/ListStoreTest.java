package ru.job4j.sync;

import org.junit.Test;

import static org.junit.Assert.*;

public class ListStoreTest {
    @Test
    public void add() {
        Store store = new ListStore();
        UserStorage storage = new UserStorage(store);
        User user = new User();
        assertTrue(storage.add(user));
    }

    @Test
    public void update() {
        Store store = new ListStore();
        UserStorage storage = new UserStorage(store);
        User user = new User();
        int id = user.getId();
        storage.add(user);
        User otherUser = new User(id);
        storage.update(otherUser);
        assertEquals(store.findById(id), otherUser);
    }

    @Test
    public void updateWhenNoUser() {
        Store store = new ListStore();
        User user = new User();
        store.add(user);
        User otherUser = new User(-1);
        assertFalse(store.update(otherUser));
    }

    @Test
    public void delete() {
        Store store = new ListStore();
        User user = new User();
        int id = user.getId();
        store.add(user);
        User otherUser = new User(id);
        store.delete(otherUser);
        assertNull(store.findById(id));
    }

    @Test
    public void deleteWhenNoUser() {
        Store store = new ListStore();
        User user = new User();
        store.add(user);
        User otherUser = new User(-1);
        assertFalse(store.delete(otherUser));
    }

    @Test
    public void whenFind() {
        Store store = new ListStore();
        User user = new User();
        store.add(user);
        int id = user.getId();
        assertEquals(store.findById(id), user);
    }

    @Test
    public void whenNoFind() {
        Store store = new ListStore();
        User user = new User();
        store.add(user);
        assertNull(store.findById(0));
    }
}