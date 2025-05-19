
package com.merchant.merchant_service.controller;

import com.merchant.merchant_service.dto.ProductCreateDTO;
import com.merchant.merchant_service.dto.ProductDTO;
import com.merchant.merchant_service.dto.ProductPatchDTO;
import com.merchant.merchant_service.exception.custom.BadRequestException;
import com.merchant.merchant_service.service.ProductService;
import com.merchant.merchant_service.utils.RequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final RequestUtils requestUtils;

    public ProductController(ProductService productService, RequestUtils requestUtils) {
        this.productService = productService;
        this.requestUtils = requestUtils;
    }
    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }


    // ✅ Create product
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductCreateDTO dto,
                                                    HttpServletRequest request) {
        Long merchantId = requestUtils.extractMerchantId(request);
        ProductDTO created = productService.createProduct(dto, merchantId);
        return ResponseEntity.ok(created);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDTO> patchProduct(@PathVariable Long id,
                                                   @RequestBody ProductPatchDTO patchDTO,
                                                   HttpServletRequest request) {
        Long merchantId = requestUtils.extractMerchantId(request);
        productService.verifyOwnership(id, merchantId);
        ProductDTO updated = productService.patchProduct(id, patchDTO);
        return ResponseEntity.ok(updated);
    }

    // ✅ Get single product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id,
                                                 HttpServletRequest request) {
        Long merchantId = requestUtils.extractMerchantId(request);
        productService.verifyOwnership(id, merchantId);
        return productService.getProduct(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BadRequestException("Product not found with ID: " + id));
    }

    // ✅ Delete product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id,
                                              HttpServletRequest request) {
        Long merchantId = requestUtils.extractMerchantId(request);
        productService.verifyOwnership(id, merchantId);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Get products for current merchant (authenticated)
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getMyProducts(HttpServletRequest request) {
        Long merchantId = requestUtils.extractMerchantId(request);
        List<ProductDTO> products = productService.getMyProducts(merchantId);
        return ResponseEntity.ok(products);
    }

    @PatchMapping("/{id}/discount")
    public ResponseEntity<ProductDTO> applyDiscount(@PathVariable Long id,
                                                    @RequestParam Integer discount,
                                                    HttpServletRequest request) {
        Long merchantId = requestUtils.extractMerchantId(request);
        productService.verifyOwnership(id, merchantId);
        ProductDTO updatedProduct = productService.applyDiscountToProduct(id, discount);
        return ResponseEntity.ok(updatedProduct);
    }

}
