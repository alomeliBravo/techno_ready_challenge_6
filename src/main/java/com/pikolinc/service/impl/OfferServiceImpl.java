package com.pikolinc.service.impl;

import com.pikolinc.dto.offer.OfferCreateDTO;
import com.pikolinc.dto.offer.OfferResponseDTO;
import com.pikolinc.enums.OfferStatus;
import com.pikolinc.exception.NotFoundException;
import com.pikolinc.mapper.OfferMapper;
import com.pikolinc.model.Offer;
import com.pikolinc.repository.OfferRepository;
import com.pikolinc.service.OfferService;
import jakarta.validation.Valid;

import java.util.List;

public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;

    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
        offerRepository.init();
    }

    @Override
    public OfferResponseDTO saveOffer(@Valid OfferCreateDTO dto) {
        Offer offer = OfferMapper.toEntity(dto);
        return this.offerRepository.save(offer);
    }

    @Override
    public List<OfferResponseDTO> findAllOffers(){
        List<OfferResponseDTO> offers = this.offerRepository.findAll();
        if (offers.isEmpty()) return List.of();
        return offers;
    }

    @Override
    public List<OfferResponseDTO> findAllOffersByUserId(Long userId) {
        List<OfferResponseDTO> offers = this.offerRepository.findAllOffersByUserId(userId);
        if (offers.isEmpty()) return List.of();
        return offers;
    }

    @Override
    public List<OfferResponseDTO> findAllOffersByItemId(Long itemId) {
        List<OfferResponseDTO> offers = this.offerRepository.findAllOffersByItemId(itemId);
        if (offers.isEmpty()) return List.of();
        return offers;
    }

    @Override
    public OfferResponseDTO findOfferById(Long id) {
        return this.offerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Offer with id: " + id + " not found"));
    }

    @Override
    public OfferResponseDTO updateOfferById(Long id, OfferCreateDTO dto) {
        OfferResponseDTO actualOffer = this.offerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Offer with id: " + id + " not found"));
        Offer offerToUpdate = OfferMapper.responseToEntity(id,actualOffer);
        offerToUpdate.setUserId(dto.userId());
        offerToUpdate.setItemId(dto.itemId());
        offerToUpdate.setStatus(dto.offerStatus());
        offerToUpdate.setAmount(dto.amount());
        this.offerRepository.update(id, offerToUpdate);
        return this.findOfferById(id);
    }

    @Override
    public OfferResponseDTO updateOfferStatus(Long id, OfferStatus status) {
        OfferResponseDTO offer = this.offerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Offer with id: " + id + " not found"));
        this.offerRepository.updateOfferStatus(id, status);
        return this.findOfferById(id);
    }

    @Override
    public Boolean offerExist(Long id) {
        return this.offerExist(id);
    }

    @Override
    public Boolean deleteOfferById(Long id) {
        return this.offerRepository.delete(id);
    }

}
