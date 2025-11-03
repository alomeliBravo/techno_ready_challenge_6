package com.pikolinc.repository.impl;

import com.pikolinc.config.DatabaseProvider;
import com.pikolinc.dao.JdbiItemDAO;
import com.pikolinc.dto.item.ItemResponseDTO;
import com.pikolinc.exception.NotFoundException;
import com.pikolinc.model.Item;
import com.pikolinc.repository.ItemRepository;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Optional;

public class JdbiItemRepository implements ItemRepository {
    private final Jdbi jdbi;

    public JdbiItemRepository(DatabaseProvider databaseProvider) {
        this.jdbi = (Jdbi) databaseProvider.getConnection();
    }

    @Override
    public void init(){
        this.jdbi.useExtension(JdbiItemDAO.class, dao -> dao.init());
    }

    @Override
    public ItemResponseDTO save(Item item) {
        long id = this.jdbi.withExtension(JdbiItemDAO.class, dao -> dao.save(item));

        return this.jdbi.withExtension(JdbiItemDAO.class, dao -> dao.findById(id))
                .orElseThrow(() -> new NotFoundException("Item with id: " + id + " not found"));
    }

    @Override
    public List<ItemResponseDTO> findAll() {
        return this.jdbi.withExtension(JdbiItemDAO.class, dao -> dao.findAll());
    }

    @Override
    public Optional<ItemResponseDTO> findById(Long id) {
        return this.jdbi.withExtension(JdbiItemDAO.class, dao -> dao.findById(id));
    }

    @Override
    public Optional<ItemResponseDTO> update(Long id, Item item) {
        int rows = this.jdbi.withExtension(JdbiItemDAO.class, dao -> dao.update(id, item));
        if (rows == 0) return Optional.empty();
        return findById(id);
    }

    @Override
    public boolean delete(Long id) {
        return this.jdbi.withExtension(JdbiItemDAO.class, dao -> dao.delete(id) > 0);
    }

    @Override
    public int itemExist(Long id) {
        return this.jdbi.withExtension(JdbiItemDAO.class, dao -> dao.itemExist(id));
    }

}
