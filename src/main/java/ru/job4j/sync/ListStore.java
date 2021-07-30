package ru.job4j.sync;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class ListStore implements Store {
    @GuardedBy("this")
    private final Map<Integer, User> userList;

    public ListStore() {
        userList = new HashMap<>();
    }

    @Override
    public synchronized boolean add(User user) {
        return userList.put(user.getId(), user) == null;
    }

    @Override
    public synchronized boolean update(User user) {
        if (!userList.containsKey(user.getId())) {
            return false;
        }
        userList.put(user.getId(), user);
        return true;
    }

    @Override
    public synchronized boolean delete(User user) {
        return userList.remove(user.getId()) != null;
    }

    @Override
    public synchronized User findById(int id) {
        return userList.get(id);
    }
}
