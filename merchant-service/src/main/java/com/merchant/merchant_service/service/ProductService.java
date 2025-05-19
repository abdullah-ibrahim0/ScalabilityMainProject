package com.merchant.merchant_service.service;

import com.merchant.merchant_service.dto.ProductCreateDTO;
import com.merchant.merchant_service.dto.ProductDTO;
import com.merchant.merchant_service.dto.ProductPatchDTO;
import com.merchant.merchant_service.entity.Merchant;
import com.merchant.merchant_service.entity.Product;
import com.merchant.merchant_service.event.ProductChangedEvent;
import com.merchant.merchant_service.exception.custom.BadRequestException;
import com.merchant.merchant_service.exception.custom.ResourceNotFoundException;
import com.merchant.merchant_service.mapper.ProductMapper;
import com.merchant.merchant_service.repository.MerchantRepository;
import com.merchant.merchant_service.repository.ProductRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final MerchantRepository merchantRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ProductService(ProductRepository productRepository,
                          MerchantRepository merchantRepository,
                          ApplicationEventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.merchantRepository = merchantRepository;
        this.eventPublisher = eventPublisher;
    }

    public Product getProductEntity(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }


    @Transactional
    public ProductDTO patchProduct(Long id, ProductPatchDTO patchDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        try {
            if (patchDTO.getName() != null) product.setName(patchDTO.getName());
            if (patchDTO.getDescription() != null) product.setDescription(patchDTO.getDescription());
            if (patchDTO.getPrice() != null) product.setPrice(patchDTO.getPrice());
            if (patchDTO.getStock() != null) product.setStock(patchDTO.getStock());
            if (patchDTO.getDiscount() != null) {
                Integer discount = patchDTO.getDiscount();
                if (discount < 0 || discount > 100)
                    throw new BadRequestException("Discount must be between 0 and 100");
                product.setDiscount(discount);
            }
            if (patchDTO.getCategory() != null) product.setCategory(patchDTO.getCategory());

            return saveAndPublishUpdatedProduct(product);
        } catch (Exception e) {
            throw new BadRequestException("Failed to patch product: " + e.getMessage());
        }
    }


    @Transactional
    public ProductDTO createProduct(ProductCreateDTO dto, Long merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found for user ID: " + merchantId));

        try {
            Product product = ProductMapper.toEntity(dto, merchant);
            Product saved = productRepository.save(product);
            eventPublisher.publishEvent(new ProductChangedEvent(this, saved, "created"));
            return ProductMapper.toDTO(saved);
        } catch (Exception e) {
            throw new BadRequestException("Failed to create product: " + e.getMessage());
        }
    }

    public ProductDTO saveAndPublishUpdatedProduct(Product product) {
        Product updated = productRepository.save(product);
        eventPublisher.publishEvent(new ProductChangedEvent(this, updated, "updated"));
        return ProductMapper.toDTO(updated);
    }

    @Transactional
    public ProductDTO applyDiscountToProduct(Long productId, Integer discount) {
        if (discount == null || discount < 0 || discount > 100) {
            throw new BadRequestException("Discount must be between 0 and 100");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

        product.setDiscount(discount);
        return saveAndPublishUpdatedProduct(product);
    }



    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        try {
            productRepository.delete(product);
            eventPublisher.publishEvent(new ProductChangedEvent(this, product, "deleted"));
        } catch (Exception e) {
            throw new BadRequestException("Failed to delete product: " + e.getMessage());
        }
    }

    public Optional<ProductDTO> getProduct(Long id) {
        return productRepository.findById(id)
                .map(ProductMapper::toDTO);
    }

    public List<ProductDTO> getMyProducts(Long merchantId) {
        return productRepository.findAllByMerchantId(merchantId)
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    public void verifyOwnership(Long productId, Long merchantId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

        if (!product.getMerchant().getId().equals(merchantId)) {
            throw new BadRequestException("Access denied: Product does not belong to your merchant account.");
        }
    }


}
