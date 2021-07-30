package ru.job4j.sync;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class UserStorage {
    private volatile Store store;

    public UserStorage(Store store) {
        this.store = store;
    }

    public boolean add(User user) {
        return store.add(user);
    }

    public boolean update(User user) {
      return store.update(user);
    }

    public boolean delete(User user) {
        return store.delete(user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User source = store.findById(fromId);
        User target = store.findById(toId);
        if (source == null || target == null) {
            return false;
        }
        int sourceBalance = source.getAmount();
        if (sourceBalance < amount) {
            return false;
        }
        source.setAmount(source.getAmount() - amount);
        target.setAmount(target.getAmount() + amount);
        return true;
    }
}
