package com.merchant.merchant_service.factory;

import com.merchant.merchant_service.dto.MerchantCreateDTO;
import com.merchant.merchant_service.enums.MerchantType;

public class MerchantFactory {
    public static MerchantInterface createMerchant(MerchantCreateDTO dto) {
        MerchantType type = dto.getType();
        return switch (type) {
            case REGULAR -> new RegularMerchant(dto);
            case PREMIUM -> new PremiumMerchant(dto);
            default -> throw new IllegalArgumentException("Invalid merchant type: " + type);
        };
    }
}


