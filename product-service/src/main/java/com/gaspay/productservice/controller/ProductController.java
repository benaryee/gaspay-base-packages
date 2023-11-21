package com.gaspay.productservice.controller;


import com.gaspay.productservice.dto.ProductRequest;
import com.gaspay.productservice.dto.ProductResponse;
import com.gaspay.productservice.model.Product;
import com.gaspay.productservice.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest, HttpServletRequest request) {

        String sessionId = request.getSession().getId();
        Product product =  productService.createProduct(productRequest,sessionId);
        return modelMapper.map(product,ProductResponse.class);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/get-by-name")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProductsByName(@RequestParam String name) {
        log.info("About to get product by name {}",name);
        return productService.getProductByName(name);
    }

    @GetMapping("/in-radius")
    public List<ProductResponse> getProductsInRadius(@RequestParam double longitude, @RequestParam double latitude,
                                                     @RequestParam(required = false, defaultValue = "1000D") double radius) {

        List<Product> products = productService.getAllInRadiusProducts(longitude, latitude , radius);

        List<ProductResponse> productsInRadius = new ArrayList<>();

        for (Product product : products) {
                boolean inStock = productService.checkInventory(product.getId());
                if (inStock) {
                    productsInRadius.add(modelMapper.map(product,ProductResponse.class));
                }
        }

        return productsInRadius;
    }

}
