package com.pikolinc.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {
    void init();
    T save(T model);
    List<T> findAll();
    Optional<T> findById(ID id);
    Optional<T> update(ID id, T model);
    boolean delete(ID id);
}
