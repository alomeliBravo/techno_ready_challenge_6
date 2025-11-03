package com.pikolinc.repository;

import com.pikolinc.dto.offer.OfferResponseDTO;
import com.pikolinc.enums.OfferStatus;
import com.pikolinc.model.Offer;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends Repository<OfferResponseDTO, Offer, Long> {
    List<OfferResponseDTO> findAllOffersByUserId(Long userId);
    List<OfferResponseDTO> findAllOffersByItemId(Long itemId);
    Optional<OfferResponseDTO> updateOfferStatus(Long id, OfferStatus status);
    Boolean offerExist(Long id);
}
