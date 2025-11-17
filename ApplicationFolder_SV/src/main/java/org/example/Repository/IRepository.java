//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example.Repository;

import java.util.List;

public interface IRepository<T, ID> {
    void create(T var1);

    T get(ID var1);

    void update(T var1);

    void delete(ID var1);

    List<T> findAll();

    void saveAll(List<T> var1);
}
