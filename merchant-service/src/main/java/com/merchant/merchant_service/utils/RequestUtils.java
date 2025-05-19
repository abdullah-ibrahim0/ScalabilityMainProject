package com.merchant.merchant_service.utils;

import com.merchant.merchant_service.entity.Merchant;
import com.merchant.merchant_service.exception.custom.BadRequestException;
import com.merchant.merchant_service.exception.custom.ResourceNotFoundException;
import com.merchant.merchant_service.repository.MerchantRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class RequestUtils {

    private final MerchantRepository merchantRepository;

    public RequestUtils(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public Long extractUserId(HttpServletRequest request) {
        Object raw = request.getAttribute("userId");
        if (raw == null) {
            throw new BadRequestException("Missing user ID in request.");
        }
        try {
            return Long.parseLong(raw.toString());
        } catch (NumberFormatException e) {
            throw new BadRequestException("Invalid user ID in token");
        }
    }

    public Long extractMerchantId(HttpServletRequest request) {
        Long userId = extractUserId(request);
        Merchant merchant = merchantRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found for user ID: " + userId));
        return merchant.getId();
    }
}
