package com.pikolinc.service.impl;

import com.pikolinc.dto.item.ItemCreateDTO;
import com.pikolinc.dto.item.ItemResponseDTO;
import com.pikolinc.dto.item.ItemUpdateDTO;
import com.pikolinc.exception.NotFoundException;
import com.pikolinc.mapper.ItemMapper;
import com.pikolinc.model.Item;
import com.pikolinc.repository.ItemRepository;
import com.pikolinc.service.ItemService;
import jakarta.validation.Valid;

import java.util.List;

public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public  ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
        this.itemRepository.init();
    }

    @Override
    public ItemResponseDTO saveItem(@Valid ItemCreateDTO dto) {
        Item item = ItemMapper.toEntity(dto);
        return this.itemRepository.save(item);
    }

    @Override
    public List<ItemResponseDTO> findAll() {
        List<ItemResponseDTO> items = this.itemRepository.findAll();
        if (items.isEmpty()) {
            return List.of();
        }
        return items;
    }

    @Override
    public ItemResponseDTO findById(Long id) {
        return this.itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item with id: " + id + " not found"));
    }

    @Override
    public ItemResponseDTO updateById(Long id, @Valid ItemCreateDTO dto){
        ItemResponseDTO itemResponse = this.itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item with id: " + id + " not found"));
        Item updatedItem = ItemMapper.responseToEntity(id, itemResponse);
        updatedItem.setName(dto.name());
        updatedItem.setDescription(dto.description());
        updatedItem.setPrice(dto.price());
        itemRepository.update(id,updatedItem);
        return ItemMapper.toResponseDTO(updatedItem);
    }

    @Override
    public ItemResponseDTO patchById(Long id, @Valid ItemUpdateDTO dto){
        ItemResponseDTO itemResponse = this.itemRepository.findById(id)
                .orElseThrow(() ->  new NotFoundException("Item with id: " + id + " not found"));
        Item updatedItem = ItemMapper.responseToEntity(id, itemResponse);
        ItemMapper.updateItem(updatedItem, dto);
        this.itemRepository.update(id, updatedItem);
        return ItemMapper.toResponseDTO(updatedItem);
    }

    @Override
    public Boolean itemExist(Long id){
        return this.itemRepository.itemExist(id) > 0;
    }

    @Override
    public Boolean deleteById(Long id){
        ItemResponseDTO item = this.itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item with id: " + id + " not found"));
        return this.itemRepository.delete(id);
    }
}
