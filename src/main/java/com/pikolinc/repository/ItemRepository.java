package com.pikolinc.repository;

import com.pikolinc.dto.item.ItemResponseDTO;
import com.pikolinc.model.Item;

public interface ItemRepository extends Repository<ItemResponseDTO, Item, Long> {
    int itemExist(Long id);
}
