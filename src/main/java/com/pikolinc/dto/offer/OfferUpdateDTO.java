package com.pikolinc.dto.offer;

import com.pikolinc.enums.OfferStatus;
import com.pikolinc.utils.ValidOfferStatus;
import jakarta.validation.constraints.Positive;

public class OfferUpdateDTO {
    @Positive(message = "clientId must be greater than zero")
    Long clientId;
    @Positive(message = "itemId must be greater than zero")
    Long itemId;
    @ValidOfferStatus
    OfferStatus offerStatus;

    Double amount;
}
