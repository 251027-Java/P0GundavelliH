package org.example.Repository;

import java.util.List;

public interface IRepository<T, ID> {
    void create(T entity);

    T get(ID entity);

    void update(T entity);

    void delete(ID entity);

    List<T> findAll();

    void saveAll(List<T> entities);
}
