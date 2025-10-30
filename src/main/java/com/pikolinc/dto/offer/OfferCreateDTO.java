package com.pikolinc.dto.offer;

import com.pikolinc.enums.OfferStatus;
import com.pikolinc.utils.ValidOfferStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OfferCreateDTO(
        @NotNull(message = "clientId is required")
        @Positive(message = "clientId must be greater than zero")
        Long clientId,
        @NotNull(message = "itemId is required")
        @Positive(message = "itemId must be greater than zero")
        Long itemId,
        @ValidOfferStatus()
        OfferStatus offerStatus,
        @Positive(message = "Price must be greater than zero")
        Double amount
) {}
