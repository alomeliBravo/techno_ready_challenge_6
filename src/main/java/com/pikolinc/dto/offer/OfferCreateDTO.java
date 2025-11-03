package com.pikolinc.dto.offer;

import com.pikolinc.enums.OfferStatus;
import com.pikolinc.utils.ValidOfferStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OfferCreateDTO(
        @NotNull(message = "userId is required")
        @Positive(message = "userId must be greater than zero")
        Long userId,
        @NotNull(message = "itemId is required")
        @Positive(message = "itemId must be greater than zero")
        Long itemId,
        @NotNull(message = "offerStatus is required")
        @ValidOfferStatus()
        OfferStatus offerStatus,
        @NotNull(message = "amount is required")
        @Positive(message = "Price must be greater than zero")
        Double amount
) {}
