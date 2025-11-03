package com.pikolinc.service;

import com.pikolinc.dto.item.ItemCreateDTO;
import com.pikolinc.dto.item.ItemResponseDTO;
import com.pikolinc.dto.item.ItemUpdateDTO;

import java.util.List;

public interface ItemService {
    ItemResponseDTO saveItem(ItemCreateDTO dto);
    List<ItemResponseDTO> findAll();
    ItemResponseDTO findById(Long id);
    ItemResponseDTO updateById(Long id, ItemCreateDTO dto);
    ItemResponseDTO patchById(Long id, ItemUpdateDTO dto);
    Boolean itemExist(Long id);
    Boolean deleteById(Long id);
}