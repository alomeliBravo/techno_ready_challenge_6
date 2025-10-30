package com.pikolinc.mapper;

import com.pikolinc.dto.item.ItemCreateDTO;
import com.pikolinc.dto.item.ItemResponseDTO;
import com.pikolinc.dto.item.ItemUpdateDTO;
import com.pikolinc.model.Item;

public class ItemMapper {
    public static Item toEntity(ItemCreateDTO dto) {
        if (dto == null) return null;

        return Item.builder()
                .name(dto.name())
                .description(dto.description())
                .price(dto.price())
                .build();
    }

    public static ItemResponseDTO toResponseDTO(Item item) {
        if (item == null) return null;

        return new ItemResponseDTO(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice()
        );
    }

    public static void updateItem(Item item, ItemUpdateDTO dto) {
        if (dto == null) return;

        if (dto.name() != null) item.setName(dto.name());
        if (dto.description() != null) item.setDescription(dto.description());
        if (dto.price() != null) item.setPrice(dto.price());
    }
}
