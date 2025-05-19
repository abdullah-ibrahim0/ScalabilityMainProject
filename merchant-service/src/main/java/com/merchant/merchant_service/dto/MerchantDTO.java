package com.merchant.merchant_service.dto;

import com.merchant.merchant_service.enums.MerchantType;

public class MerchantDTO {

    private Long id;
    private Long userId;
    private String name;
    private String email;
    private String phoneNumber;
    private MerchantType type;

    public MerchantDTO() {}

    public MerchantDTO(Long id, Long userId, String name, String email, String phoneNumber, MerchantType type) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public MerchantType getType() {
        return type;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setType(MerchantType type) {
        this.type = type;
    }
}
