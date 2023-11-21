package com.gaspay.repository;



import com.gaspay.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InventoryRepository extends MongoRepository<Inventory, String> {
    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
