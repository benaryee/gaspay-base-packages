package com.gaspay.productservice.util;

import com.gaspay.productservice.dto.Variant;
import com.gaspay.productservice.model.Product;
import com.gaspay.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() < 1) {

            Variant small = Variant.builder()
                    .weight(2.5)
                    .height(2.5)
                    .price(0.2D)
                    .build();

            Variant medium = Variant.builder()
                    .weight(7.0)
                    .height(7.0)
                    .price(60D)
                    .build();


            Variant large = Variant.builder()
                    .weight(11.0)
                    .height(11.0)
                    .price(90D)
                    .build();


            Variant xlarge = Variant.builder()
                    .weight(15.0)
                    .height(15.0)
                    .price(140D)
                    .build();

            List<Variant> variantList = new ArrayList<>();
            variantList.add(small);
            variantList.add(medium);
            variantList.add(large);
            variantList.add(xlarge);

            Product product = new Product();
            product.setName("Cylinder");
            product.setDescription("Gas Cylinder Description");
            product.setVariantList(variantList);

            productRepository.save(product);
        }
    }
}
