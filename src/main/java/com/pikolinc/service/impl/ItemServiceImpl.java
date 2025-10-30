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
        return ItemMapper.toResponseDTO(this.itemRepository.save(item));
    }

    @Override
    public List<ItemResponseDTO> findAll() {
        List<Item> items = this.itemRepository.findAll();
        if (items.isEmpty()) {
            return List.of();
        }
        return items.stream().map(ItemMapper::toResponseDTO).toList();
    }

    @Override
    public ItemResponseDTO findById(Long id) {
        Item item = this.itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item with id: " + id + " not found"));
        return ItemMapper.toResponseDTO(item);
    }

    @Override
    public ItemResponseDTO updateById(Long id, @Valid ItemCreateDTO dto){
        Item item = this.itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item with id: " + id + " not found"));
        item.setName(dto.name());
        item.setDescription(dto.description());
        item.setPrice(dto.price());
        itemRepository.update(id,item);
        return ItemMapper.toResponseDTO(item);
    }

    @Override
    public ItemResponseDTO patchById(Long id, @Valid ItemUpdateDTO dto){
        Item item = this.itemRepository.findById(id)
                .orElseThrow(() ->  new NotFoundException("Item with id: " + id + " not found"));
        ItemMapper.updateItem(item, dto);
        this.itemRepository.update(id, item);
        return ItemMapper.toResponseDTO(item);
    }

    @Override
    public Boolean itemExist(Long id){
        return this.itemRepository.itemExist(id) > 0 ? true : false;
    }

    @Override
    public Boolean deleteById(Long id){
        Item item = this.itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item with id: " + id + " not found"));
        return this.itemRepository.delete(id);
    }
}
