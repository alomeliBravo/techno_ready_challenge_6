package com.pikolinc.repository.impl;

import com.pikolinc.config.DatabaseProvider;
import com.pikolinc.dao.JdbiOffersDAO;
import com.pikolinc.dto.offer.OfferCreateDTO;
import com.pikolinc.dto.offer.OfferResponseDTO;
import com.pikolinc.enums.OfferStatus;
import com.pikolinc.exception.NotFoundException;
import com.pikolinc.model.Offer;
import com.pikolinc.repository.OfferRepository;
import jakarta.validation.Valid;
import org.jdbi.v3.core.Jdbi;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public class JdbiOfferRepository implements OfferRepository {
    private final Jdbi jdbi;

    public JdbiOfferRepository (DatabaseProvider databaseProvider) {
        this.jdbi = (Jdbi) databaseProvider.getConnection();
    }

    @Override
    public void init(){
        this.jdbi.useExtension(JdbiOffersDAO.class, dao -> dao.init());
    }

    @Override
    public OfferResponseDTO save(Offer offer) {
        long id = this.jdbi.withExtension(JdbiOffersDAO.class, dao -> dao.save(offer));

        return this.jdbi.withExtension(JdbiOffersDAO.class, dao -> dao.findById(id))
                .orElseThrow(() -> new NotFoundException("Offer with id: " +  id + " not found"));
    }

    @Override
    public List<OfferResponseDTO> findAll() {
        return this.jdbi.withExtension(JdbiOffersDAO.class, dao -> dao.findAll());
    }

    @Override
    public List<OfferResponseDTO> findAllOffersByUserId(Long userId) {
        return this.jdbi.withExtension(JdbiOffersDAO.class, dao -> dao.findAllByUserId(userId));
    }

    @Override
    public List<OfferResponseDTO> findAllOffersByItemId(Long itemId) {
        return this.jdbi.withExtension(JdbiOffersDAO.class, dao -> dao.findAllByItemId(itemId));
    }

    @Override
    public Optional<OfferResponseDTO> findById(Long id) {
        return this.jdbi.withExtension(JdbiOffersDAO.class, dao -> dao.findById(id));
    }

    @Override
    public Optional<OfferResponseDTO> update(Long id, Offer offer) {
        int rows = this.jdbi.withExtension(JdbiOffersDAO.class, dao -> dao.updateOffer(id, offer));
        if (rows == 0) {
            return Optional.empty();
        }
        return this.findById(id);
    }

    @Override
    public Optional<OfferResponseDTO> updateOfferStatus(Long id, OfferStatus status) {
        int rows  = this.jdbi.withExtension(JdbiOffersDAO.class, dao -> dao.updateOfferStatus(id, status));
        if (rows == 0) {
            return Optional.empty();
        }
        return this.findById(id);
    }

    @Override
    public boolean delete(Long id){
        return this.jdbi.withExtension(JdbiOffersDAO.class, dao -> dao.deleteOffer(id) > 0) ;
    }

    @Override
    public Boolean offerExist(Long id) {
        return this.jdbi.withExtension(JdbiOffersDAO.class, dao -> dao.offerExists(id) > 0);
    }
}
