package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (key, value) -> {
                if (value.getVersion() == model.getVersion()) {
                    Base current = new Base(model.getId(), model.getVersion() + 1);
                    current.setName(model.getName());
                    return current;
                }
            throw new OptimisticException("Versions are not equal");
        }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }
}
