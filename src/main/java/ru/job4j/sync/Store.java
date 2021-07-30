package ru.job4j.sync;

public interface Store {
    boolean add(User user);
    boolean update(User user);
    boolean delete(User user);
    User findById(int id);
}
