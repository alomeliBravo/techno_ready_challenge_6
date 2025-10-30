package com.pikolinc.dto.offer;

import com.pikolinc.enums.OfferStatus;

public class OfferResponseDTO {
    Long id;
    Long clientId;
    String clientName;
    String clientEmail;
    Long itemId;
    String itemName;
    String itemDescription;
    Double initalPrice;
    OfferStatus offerStatus;
    Double amountOffer;
}
