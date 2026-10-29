package service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import model.annotation.Id;

public class Repository<T> {
    private final HashMap<String, T> data = new HashMap<>();

    private String getId(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null.");
        }

        for (Class<?> type = entity.getClass();
             type != null;
             type = type.getSuperclass()) {
            for (Field field : type.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    try {
                        field.setAccessible(true);
                        Object id = field.get(entity);
                        if (id == null) {
                            throw new IllegalArgumentException("Null @Id value.");
                        }
                        return id.toString();
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    }
                }
            }
        }

        throw new IllegalArgumentException("No @Id field found.");
    }

    public synchronized T save(T entity) {
        data.put(getId(entity), entity);
        return entity;
    }

    @SafeVarargs
    public final synchronized void saveAll(T... items) {
        for (T item : items) {
            save(item);
        }
    }

    public synchronized T findById(String id) {
        return data.get(id);
    }

    public synchronized ArrayList<T> findAll() {
        return new ArrayList<>(data.values());
    }

    public synchronized boolean delete(String id) {
        return data.remove(id) != null;
    }
}