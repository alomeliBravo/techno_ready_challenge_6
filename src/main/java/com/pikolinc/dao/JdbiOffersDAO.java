package com.pikolinc.dao;

import com.pikolinc.dto.offer.OfferResponseDTO;
import com.pikolinc.enums.OfferStatus;
import com.pikolinc.model.Offer;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(OfferResponseDTO.class)
public interface JdbiOffersDAO {

    @SqlUpdate("""
        CREATE TABLE IF NOT EXISTS offers (
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            userId BIGINT NOT NULL,
            itemId BIGINT NOT NULL,
            status VARCHAR(50) NOT NULL,
            amount DECIMAL(10,2) NOT NULL,
            FOREIGN KEY (userId) REFERENCES users(id),
            FOREIGN KEY (itemId) REFERENCES items(id)
        )
    """)
    public void init();

    @SqlUpdate("INSERT INTO offers (userId, itemId, status, amount) VALUES (:userId, :itemId, :status, :amount)")
    @GetGeneratedKeys
    long save(@BindBean Offer offer);

    @SqlQuery("""
        SELECT
            o.id AS id,
            u.id AS userId,
            u.name AS userName,
            u.email AS userEmail,
            i.id AS itemId,
            i.name AS itemName,
            i.description AS itemDescription,
            i.price AS initialPrice,
            o.status AS offerStatus,
            o.amount AS amountOffer
        FROM offers o
        JOIN users u ON u.id = o.userId
        JOIN items i ON i.id = o.itemId
        WHERE o.id = :id
    """)
    Optional<OfferResponseDTO> findById(@Bind("id") Long id);

    @SqlQuery("""
        SELECT
            o.id AS id,
            u.id AS userId,
            u.name AS userName,
            u.email AS userEmail,
            i.id AS itemId,
            i.name AS itemName,
            i.description AS itemDescription,
            i.price AS initialPrice,
            o.status AS offerStatus,
            o.amount AS amountOffer
        FROM offers o
        JOIN users u ON u.id = o.userId
        JOIN items i ON i.id = o.itemId
        ORDER BY o.id
    """)
    List<OfferResponseDTO> findAll();

    @SqlQuery("""
        SELECT COUNT(*) 
        FROM offers 
        WHERE id = :id
    """)
    int offerExists(@Bind("id") Long id);

    @SqlQuery("""
        SELECT
            o.id AS id,
            u.id AS userId,
            u.name AS userName,
            u.email AS userEmail,
            i.id AS itemId,
            i.name AS itemName,
            i.description AS itemDescription,
            i.price AS initialPrice,
            o.status AS offerStatus,
            o.amount AS amountOffer
        FROM offers o
        JOIN users u ON u.id = o.userId
        JOIN items i ON i.id = o.itemId
        WHERE o.userId = :userId
        ORDER BY o.id
    """)
    List<OfferResponseDTO> findAllByUserId(@Bind("userId") Long userId);

    @SqlQuery("""
        SELECT
            o.id AS id,
            u.id AS userId,
            u.name AS userName,
            u.email AS userEmail,
            i.id AS itemId,
            i.name AS itemName,
            i.description AS itemDescription,
            i.price AS initialPrice,
            o.status AS offerStatus,
            o.amount AS amountOffer
        FROM offers o
        JOIN users u ON u.id = o.userId
        JOIN items i ON i.id = o.itemId
        WHERE o.itemId = :itemId
        ORDER BY o.id
    """)
    List<OfferResponseDTO> findAllByItemId(@Bind("itemId") long itemId);

    @SqlUpdate("""
        UPDATE offers
        SET 
            status = :status,
            amount = :amount
        WHERE id = :id
    """)
    int updateOffer(@Bind("id") long id, @Bind("status")OfferStatus status, @Bind("amount") Double amount);

    @SqlUpdate("""
        UPDATE offers
        SET 
            status = :status
        WHERE id = :id        
    """)
    int updateOfferStatus(@Bind("id") long id, @Bind("status")OfferStatus status);

    @SqlUpdate("DELETE FROM offers WHERE id = :id")
    int deleteOffer(@Bind("id") Long id);
}
