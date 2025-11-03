package com.pikolinc.service;

import com.pikolinc.dto.offer.OfferCreateDTO;
import com.pikolinc.dto.offer.OfferResponseDTO;
import com.pikolinc.enums.OfferStatus;

import java.util.List;

public interface OfferService {
    OfferResponseDTO saveOffer(OfferCreateDTO offerCreateDTO);
    List<OfferResponseDTO> findAllOffers();
    List<OfferResponseDTO> findAllOffersByUserId(Long userId);
    List<OfferResponseDTO> findAllOffersByItemId(Long itemId);
    OfferResponseDTO findOfferById(Long id);
    OfferResponseDTO updateOfferById(Long id, OfferCreateDTO dto);
    OfferResponseDTO updateOfferStatus(Long id, OfferStatus status);
    Boolean offerExist(Long id);
    Boolean deleteOfferById(Long id);
}
