package com.merchant.merchant_service.factory;

import com.merchant.merchant_service.entity.Merchant;
import com.merchant.merchant_service.enums.MerchantType;

public interface MerchantInterface {
    MerchantType getType(); // ✅ return enum instead of String
    Merchant toEntity();
}
