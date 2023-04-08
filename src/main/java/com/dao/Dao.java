package com.dao;

import java.util.List;

public interface Dao<T> {
    List<T> index();

    T show(int id);

    void save(T t);

    void update(int id, T t);

    void delete(int id);
}
