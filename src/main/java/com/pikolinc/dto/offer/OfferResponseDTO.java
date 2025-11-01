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
    Long userId;
    String userName;
    String userEmail;
    Long itemId;
    String itemName;
    String itemDescription;
    Double initialPrice;
    OfferStatus offerStatus;
    Double amountOffer;
}
