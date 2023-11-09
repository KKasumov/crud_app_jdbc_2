package com.kyamran.app.repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    T save(T entity);
    T update(T entity);
    T getById(ID id);
    void deleteById(ID id);
    List<T> getAll();
}
