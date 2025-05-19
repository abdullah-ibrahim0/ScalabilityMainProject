package com.merchant.merchant_service.service;

import com.merchant.merchant_service.dto.MerchantCreateDTO;
import com.merchant.merchant_service.dto.MerchantDTO;
import com.merchant.merchant_service.entity.Merchant;
import com.merchant.merchant_service.exception.custom.BadRequestException;
import com.merchant.merchant_service.exception.custom.ResourceNotFoundException;
import com.merchant.merchant_service.factory.MerchantFactory;
import com.merchant.merchant_service.factory.MerchantInterface;
import com.merchant.merchant_service.repository.MerchantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MerchantService {

    private final MerchantRepository merchantRepository;

    public MerchantService(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public void registerMerchant(MerchantCreateDTO dto) {
        if (merchantRepository.findByUserId(dto.getUserId()).isPresent()) {
            throw new BadRequestException("Merchant already exists for user ID: " + dto.getUserId());
        }

        MerchantInterface merchant = MerchantFactory.createMerchant(dto);
        merchantRepository.save(merchant.toEntity());
    }


    public List<MerchantDTO> getAllMerchants() {
        List<Merchant> merchants = merchantRepository.findAll();
        return merchants.stream()
                .map(entity -> new MerchantDTO(
                        entity.getId(),
                        entity.getUserId(),
                        entity.getName(),
                        entity.getEmail(),
                        entity.getPhoneNumber(),
                        entity.getType()
                ))
                .collect(Collectors.toList());
    }

    public MerchantDTO getMerchantByUserId(Long userId) {
        Merchant entity = merchantRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with userId: " + userId));
        return new MerchantDTO(
                entity.getId(),
                entity.getUserId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getType()
        );
    }

    public MerchantDTO getMerchantById(Long id) {
        Merchant entity = merchantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with ID: " + id));
        return new MerchantDTO(
                entity.getId(),
                entity.getUserId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getType()
        );
    }

    public void updateMerchant(Long id, MerchantCreateDTO dto) {
        Merchant existing = merchantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with ID: " + id));

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setPhoneNumber(dto.getPhoneNumber());
        existing.setType(dto.getType());

        merchantRepository.save(existing);
    }

    public void deleteMerchant(Long id) {
        if (!merchantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + id);
        }
        merchantRepository.deleteById(id);
    }
}
