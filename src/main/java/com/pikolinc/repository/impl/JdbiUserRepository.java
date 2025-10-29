package com.pikolinc.repository.impl;

import com.pikolinc.config.DatabaseProvider;
import com.pikolinc.dao.JdbiUserDAO;
import com.pikolinc.model.User;
import com.pikolinc.repository.UserRepository;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Optional;

public class JdbiUserRepository implements UserRepository {

    private final Jdbi jdbi;

    public JdbiUserRepository(DatabaseProvider dataBaseProvider) {
        this.jdbi = (Jdbi) dataBaseProvider.getConnection();
    }

    @Override
    public void init() {
        this.jdbi.useExtension(JdbiUserDAO.class, JdbiUserDAO::init);
    }

    @Override
    public User save(User user) {
        long id = this.jdbi.withExtension(JdbiUserDAO.class, dao -> dao.save(user));

        return findById(id)
                .orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }

    @Override
    public List<User> findAll(){
        return this.jdbi.withExtension(JdbiUserDAO.class, dao -> dao.findAll());
    }

    @Override
    public Optional<User> findById(Long id) {
        return this.jdbi.withExtension(JdbiUserDAO.class, dao -> dao.findById(id));
    }

    @Override
    public Optional<User> update(Long id, User user) {
        int rows = this.jdbi.withExtension(JdbiUserDAO.class, dao -> dao.update(id, user));
        if (rows == 0 ) return Optional.empty();
        return findById(id);
    }

    @Override
    public boolean delete(Long id) {
        return this.jdbi.withExtension(JdbiUserDAO.class, dao -> dao.delete(id) > 0);
    }

    public Optional<User> findByEmail(String email) {
        return this.jdbi.withExtension(JdbiUserDAO.class, dao -> dao.findByEmail(email));
    }

    public int userExist(Long id) {
        return this.jdbi.withExtension(JdbiUserDAO.class, dao -> dao.userExist(id));
    }

}
