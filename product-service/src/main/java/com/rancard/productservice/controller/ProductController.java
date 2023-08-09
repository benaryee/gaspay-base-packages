package com.rancard.productservice.controller;


import com.rancard.productservice.dto.ProductRequest;
import com.rancard.productservice.dto.ProductResponse;
import com.rancard.productservice.model.Product;
import com.rancard.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {

        Product product =  productService.createProduct(productRequest);
        return modelMapper.map(product,ProductResponse.class);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/in-radius")
    public List<ProductResponse> getProductsInRadius(@RequestParam double longitude, @RequestParam double latitude, @RequestParam double radius) {

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
