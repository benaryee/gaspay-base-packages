package com.rancard.productservice.service;

import com.rancard.productservice.dto.ProductRequest;
import com.rancard.productservice.dto.ProductResponse;
import com.rancard.productservice.model.Product;
import com.rancard.productservice.model.payload.User;
import com.rancard.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;

    public Product createProduct(ProductRequest productRequest, String sessionId){


        if(productRequest.getUser().getAddress() == null && productRequest.getUser().getMsisdn() == null) {
            throw new RuntimeException("Address is required");
        }

        if(productRequest.getUser().getAddress() == null && productRequest.getUser().getMsisdn() != null) {
            //Get user address from user service
            log.info("Getting user address from user service");
            User user = userService.getUserAddress(productRequest.getUser().getMsisdn(), sessionId);
            if(user != null){
                productRequest.getUser().setAddress(user.getAddress());
            }

        }

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .address(productRequest.getUser().getAddress())
                .price(productRequest.getPrice())
                .build();

        log.info("Product {} is saved", product.getId());
        return productRepository.save(product);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public List<Product> getAllInRadiusProducts(double longitude, double latitude , double rangeInKm) {

        double minLat = latitude - (rangeInKm / 111.12); // 1 degree of latitude is approximately 111.12 km
        double maxLat = latitude + (rangeInKm / 111.12);
        double minLon = longitude - (rangeInKm / (111.12 * Math.cos(Math.toRadians(latitude))));
        double maxLon = longitude + (rangeInKm / (111.12 * Math.cos(Math.toRadians(latitude))));

        return productRepository.findByAddressLatitudeBetweenAndAddressLongitudeBetween(minLat, maxLat, minLon, maxLon);

    }

    public boolean checkInventory(String id) {
        return true;
    }
}
