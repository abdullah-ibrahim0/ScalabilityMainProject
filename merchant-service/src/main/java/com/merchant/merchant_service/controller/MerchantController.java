package com.merchant.merchant_service.controller;

import com.merchant.merchant_service.dto.MerchantCreateDTO;
import com.merchant.merchant_service.dto.MerchantDTO;
import com.merchant.merchant_service.service.MerchantService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/merchants")
public class MerchantController {

    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @PostMapping
    public ResponseEntity<String> createMerchant(@Valid @RequestBody MerchantCreateDTO dto) {
        merchantService.registerMerchant(dto);
        return ResponseEntity.ok("Merchant created successfully.");
    }


    @GetMapping
    public ResponseEntity<List<MerchantDTO>> getAllMerchants() {
        return ResponseEntity.ok(merchantService.getAllMerchants());
    }
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<MerchantDTO> getMerchantByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(merchantService.getMerchantByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchantDTO> getMerchantById(@PathVariable Long id) {
        return ResponseEntity.ok(merchantService.getMerchantById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateMerchant(@PathVariable Long id, @RequestBody MerchantCreateDTO dto) {
        merchantService.updateMerchant(id, dto);
        return ResponseEntity.ok("Merchant updated successfully.");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMerchant(@PathVariable Long id) {
        merchantService.deleteMerchant(id);
        return ResponseEntity.ok("Merchant deleted successfully.");
    }



}
