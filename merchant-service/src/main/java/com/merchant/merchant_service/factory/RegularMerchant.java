package com.merchant.merchant_service.factory;

import com.merchant.merchant_service.dto.MerchantCreateDTO;
import com.merchant.merchant_service.entity.Merchant;
import com.merchant.merchant_service.enums.MerchantType;

public class RegularMerchant implements MerchantInterface {
    private final MerchantCreateDTO dto;

    public RegularMerchant(MerchantCreateDTO dto) {
        this.dto = dto;
    }

    @Override
    public MerchantType getType() {
        return MerchantType.REGULAR;
    }

    @Override
    public Merchant toEntity() {
        return new Merchant(
                dto.getUserId(),
                dto.getName(),
                dto.getEmail(),
                dto.getPhoneNumber(),
                MerchantType.REGULAR
        );
    }
}
