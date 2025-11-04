package com.pikolinc.service.impl;

import com.pikolinc.dto.item.ItemResponseDTO;
import com.pikolinc.dto.offer.OfferCreateDTO;
import com.pikolinc.dto.offer.OfferResponseDTO;
import com.pikolinc.enums.OfferStatus;
import com.pikolinc.exception.NotFoundException;
import com.pikolinc.mapper.OfferMapper;
import com.pikolinc.model.Offer;
import com.pikolinc.repository.OfferRepository;
import com.pikolinc.service.ItemService;
import com.pikolinc.service.OfferService;
import com.pikolinc.service.UserService;
import com.pikolinc.web.OfferWebSocketHandler;
import jakarta.validation.Valid;

import java.util.List;

public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final ItemService itemService;
    private final UserService userService;

    public OfferServiceImpl(OfferRepository offerRepository, ItemService itemService, UserService userService) {
        this.offerRepository = offerRepository;
        offerRepository.init();
        this.itemService = itemService;
        this.userService = userService;
    }

    @Override
    public OfferResponseDTO saveOffer(@Valid OfferCreateDTO dto) {
        if (!userService.userExist(dto.userId())) {
            throw new NotFoundException("User with id: " + dto.userId() + " not found");
        }

        if (!itemService.itemExist(dto.itemId())) {
            throw new NotFoundException("Item with id: " + dto.itemId() + " not found");
        }
        Offer offer = OfferMapper.toEntity(dto);
        OfferResponseDTO offerSaved = this.offerRepository.save(offer);
        notifyPriceUpdate(dto.itemId());
        return offerSaved;
    }

    @Override
    public List<OfferResponseDTO> findAllOffers(){
        List<OfferResponseDTO> offers = this.offerRepository.findAll();
        if (offers.isEmpty()) return List.of();
        return offers;
    }

    @Override
    public List<OfferResponseDTO> findAllOffersByUserId(Long userId) {
        if (!userService.userExist(userId)) {
            throw new NotFoundException("User with id: " + userId + " not found");
        }
        List<OfferResponseDTO> offers = this.offerRepository.findAllOffersByUserId(userId);
        if (offers.isEmpty()) return List.of();
        return offers;
    }

    @Override
    public List<OfferResponseDTO> findAllOffersByItemId(Long itemId) {
        if (!itemService.itemExist(itemId)) {
            throw new NotFoundException("Item with id: " + itemId + " not found");
        }
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
        if (!userService.userExist(dto.userId())) {
            throw new NotFoundException("User with id: " + dto.userId() + " not found");
        }
        if (!itemService.itemExist(dto.itemId())) {
            throw new NotFoundException("Item with id: " + dto.itemId() + " not found");
        }
        Offer offerToUpdate = OfferMapper.responseToEntity(id,actualOffer);
        offerToUpdate.setUserId(dto.userId());
        offerToUpdate.setItemId(dto.itemId());
        offerToUpdate.setStatus(dto.offerStatus());
        offerToUpdate.setAmount(dto.amount());
        this.offerRepository.update(id, offerToUpdate);
        notifyPriceUpdate(dto.itemId());
        return this.findOfferById(id);
    }

    @Override
    public OfferResponseDTO updateOfferStatus(Long id, OfferStatus status) {
        OfferResponseDTO offer = this.offerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Offer with id: " + id + " not found"));
        this.offerRepository.updateOfferStatus(id, status);
        notifyPriceUpdate(offer.getItemId());
        return this.findOfferById(id);
    }

    @Override
    public Boolean offerExist(Long id) {
        return this.offerRepository.offerExist(id);
    }

    @Override
    public Boolean deleteOfferById(Long id) {
        OfferResponseDTO offer = this.offerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Offer with id: " + id + " not found"));
        boolean deleted = this.offerRepository.delete(id);
        if (deleted) notifyPriceUpdate(offer.getItemId());
        return deleted;
    }

    private void notifyPriceUpdate(Long itemId) {
        try {
            ItemResponseDTO item = this.itemService.findById(itemId);
            List<OfferResponseDTO> offers = this.offerRepository.findAllOffersByItemId(itemId);

            double currentPrice = item.getPrice();
            if (!offers.isEmpty()) {
                double maxOffer = offers.stream()
                        .mapToDouble(OfferResponseDTO::getAmountOffer)
                        .max()
                        .orElse(item.getPrice());
                if (maxOffer > currentPrice) {
                    currentPrice = maxOffer;
                }
            }

            OfferWebSocketHandler.notifyPriceUpdate(itemId, currentPrice, item.getPrice());
        } catch (Exception e) {
            System.err.println("Error notifying price update " + e.getMessage());
        }
    }
}
