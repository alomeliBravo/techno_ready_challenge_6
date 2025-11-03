package com.pikolinc.dao;

import com.pikolinc.dto.user.UserResponseDTO;
import com.pikolinc.model.User;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapperFactory;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(UserResponseDTO.class)
public interface JdbiUserDAO {
    @SqlUpdate("""
        CREATE TABLE IF NOT EXISTS users (
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            name VARCHAR(100) NOT NULL,
            email VARCHAR(100) NOT NULL UNIQUE
        )        
    """)
    void init();

    @SqlUpdate("INSERT INTO users (name, email) VALUES (:name, :email)")
    @GetGeneratedKeys
    long save(@BindBean User user);

    @SqlQuery("SELECT * FROM users WHERE id = :id")
    Optional<UserResponseDTO> findById(@Bind("id") Long id);

    @SqlQuery("SELECT * FROM users")
    List<UserResponseDTO> findAll();

    @SqlQuery("SELECT * FROM users where email = :email")
    Optional<UserResponseDTO> findByEmail(@Bind("email") String email);

    @SqlQuery("SELECT COUNT(*) FROM users WHERE email = :email")
    int emailTaken(@Bind("email") String email);

    @SqlQuery("SELECT COUNT(*) FROM users WHERE id = :id")
    int userExist(@Bind("id") Long id);

    @SqlUpdate("UPDATE users SET name = :name, email = :email WHERE id = :id")
    int update(@Bind("id") Long id, @BindBean User user);

    @SqlUpdate("DELETE from users where id = :id")
    int delete(@Bind("id") Long id);
}
