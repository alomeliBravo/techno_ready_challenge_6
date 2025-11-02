package com.pikolinc.mapper;

import com.pikolinc.dto.offer.OfferCreateDTO;
import com.pikolinc.dto.offer.OfferUpdateDTO;
import com.pikolinc.model.Offer;

public class OfferMapper {
    public static Offer toEntity(OfferCreateDTO dto) {
        if  (dto == null) {
            return null;
        }
        return Offer.builder()
                .userId(dto.userId())
                .itemId(dto.itemId())
                .status(dto.offerStatus())
                .amount(dto.amount())
                .build();
    }

    public static void updateEntity(Offer offer, OfferUpdateDTO dto) {
        if (dto == null) return;

        if (dto.userId() != null) offer.setUserId(dto.userId());
        if (dto.itemId() != null) offer.setItemId(dto.itemId());
        if (dto.offerStatus() != null) offer.setStatus(dto.offerStatus());
        if (dto.amount() != null) offer.setAmount(dto.amount());
    }
}
