package ru.job4j.sync;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class ListStore implements Store {
    @GuardedBy("this")
    private final Map<Integer, User> userList = new HashMap<>();

    @Override
    public synchronized boolean add(User user) {
        return userList.put(user.getId(), user) == null;
    }

    @Override
    public synchronized boolean update(User user) {
        return userList.replace(user.getId(), user) != null;
    }

    @Override
    public synchronized boolean delete(User user) {
        return userList.remove(user.getId(), user);
    }

    @Override
    public synchronized User findById(int id) {
        return userList.get(id);
    }
}
