package com.merchant.merchant_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.merchant.merchant_service.enums.MerchantType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)

public class MerchantCreateDTO {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotNull(message = "Merchant type is required")
    private MerchantType type;

    public MerchantCreateDTO() {}

    public MerchantCreateDTO(Long userId, String name, String email, String phoneNumber, MerchantType type) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    // Getters
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
