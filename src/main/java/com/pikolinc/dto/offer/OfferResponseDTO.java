package com.pikolinc.dto.offer;

import com.pikolinc.enums.OfferStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
