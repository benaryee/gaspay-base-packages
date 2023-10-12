package com.rancard.productservice.repository;

import com.rancard.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByAddressLatitudeBetweenAndAddressLongitudeBetween(
            double minLat, double maxLat, double minLon, double maxLon
    );

    Product findByName(String name);
}
