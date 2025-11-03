package com.pikolinc.model;

import com.pikolinc.enums.OfferStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Offer {
    Long id;
    Long userId;
    Long itemId;
    OfferStatus status;
    Double amount;
}


