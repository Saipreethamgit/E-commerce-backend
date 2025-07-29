package com.gamercein.backend.repository;

import com.gamercein.backend.model.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CartRepository extends MongoRepository<CartItem, String> {
    List<CartItem> findByUserId(String userId);
    void deleteByUserIdAndProductId(String userId, int productId);
}
