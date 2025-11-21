package org.example.Repository;

import java.sql.Connection;
import java.util.List;

public interface IRepository<T, ID> {
    int create(T entity);

    T get(ID entity);

    void update(T entity);

    void delete(ID entity);

    List<T> findAll();

    void saveAll(List<T> entities);

    Connection getConnection();
}
