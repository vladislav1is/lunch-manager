package com.redfox.lunchmanager.repository.inmemory;

import com.redfox.lunchmanager.model.AbstractBaseEntity;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryBaseRepository<T extends AbstractBaseEntity> {

    private final AtomicInteger counter;
    private final Map<Integer, T> map = new ConcurrentHashMap<>();

    public InMemoryBaseRepository(AtomicInteger counter) {
        this.counter = counter;
    }

    public T save(T entity) {
        if (entity.isNew()) {
            entity.setId(counter.incrementAndGet());
            map.put(entity.getId(), entity);
            return entity;
        }
        return map.computeIfPresent(entity.getId(), (id, oldT) -> entity);
    }

    public boolean delete(int id) {
        return map.remove(id) != null;
    }

    public T get(int id) {
        return map.get(id);
    }

    public Collection<T> getCollection() {
        return map.values();
    }
}
