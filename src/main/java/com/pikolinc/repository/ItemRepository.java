package com.pikolinc.repository;

import com.pikolinc.model.Item;

public interface ItemRepository extends Repository<Item, Long> {
    int itemExist(Long id);
}
