package ru.job4j.sync;

import java.util.ArrayList;
import java.util.List;

public class ListStore implements Store {
    private final List<User> userList;

    public ListStore() {
        userList = new ArrayList<>();
    }

    @Override
    public boolean add(User user) {
        return userList.add(user);
    }

    @Override
    public boolean update(User user) {
        int index = findIndex(user);
        if (index == -1) {
            return false;
        }
        userList.remove(index);
        userList.add(index, user);
        return true;
    }

    @Override
    public boolean delete(User user) {
        int index = findIndex(user);
        if (index == -1) {
            return false;
        }
        userList.remove(index);
        return true;
    }

    @Override
    public User findById(int id) {
        return userList.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    private int findIndex(User user) {
        int index = -1;
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() == user.getId()) {
                index = i;
                break;
            }
        }
        return index;
    }
}
