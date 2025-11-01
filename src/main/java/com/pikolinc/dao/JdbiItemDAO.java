package com.pikolinc.dao;

import com.pikolinc.dto.item.ItemResponseDTO;
import com.pikolinc.model.Item;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(ItemResponseDTO.class)
public interface JdbiItemDAO {
    @SqlUpdate("""
        CREATE TABLE IF NOT EXISTS items (
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            name VARCHAR(100) NOT NULL,
            description VARCHAR(100),
            price DECIMAL(10,2)
        )
    """)
    void init();

    @SqlUpdate("INSERT INTO items (name, description, price) VALUES (:name, :description, :price)")
    @GetGeneratedKeys
    long save(@BindBean Item item);

    @SqlQuery("SELECT * FROM items WHERE id = :id")
    Optional<ItemResponseDTO> findById(@Bind("id") long id);

    @SqlQuery("SELECT * FROM items")
    List<ItemResponseDTO> findAll();

    @SqlQuery("SELECT COUNT(*) FROM items WHERE id = :id")
    int itemExist(@Bind("id") long id);

    @SqlUpdate("UPDATE items SET name = :name, description = :description, price = :price WHERE id = :id")
    int update(@Bind("id") long id, @BindBean Item item);

    @SqlUpdate("DELETE from items WHERE id = :id")
    int delete(@Bind("id") long id);
}
