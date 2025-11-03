package com.pikolinc.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<X, T, ID> {
    void init();
    X save(T model);
    List<X> findAll();
    Optional<X> findById(ID id);
    Optional<X> update(ID id, T model);
    boolean delete(ID id);
}
