package com.gamercein.backend.controller;

import com.gamercein.backend.model.CartItem;
import com.gamercein.backend.model.User;
import com.gamercein.backend.repository.CartItemRepository;
import com.gamercein.backend.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "https://e-commerce-frontend-q6t5.vercel.app"
})

public class CartController {

    private final CartItemRepository cartRepo;
    private final UserRepository userRepository;

    public CartController(CartItemRepository cartRepo, UserRepository userRepository) {
        this.cartRepo = cartRepo;
        this.userRepository = userRepository;
    }

    @GetMapping
public List<Map<String, Object>> getCartItems(Authentication authentication) {
    String username = authentication.getName();
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

    List<CartItem> cartItems = cartRepo.findByUserId(user.getId());

    return cartItems.stream().map(item -> {
        Map<String, Object> response = new HashMap<>();
        response.put("productId", item.getProductId());
        response.put("quantity", item.getQuantity());
        
        // fetch product details
        Product product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        response.put("name", product.getName());
        response.put("price", product.getPrice());
        response.put("imageUrl", product.getImageUrl());

        return response;
    }).collect(Collectors.toList());
}


    @PostMapping
public List<CartItem> addOrUpdateCartItem(@RequestBody CartItem item, Authentication authentication) {
    String username = authentication.getName();
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

    String productId = item.getProductId().trim();
    String userId = user.getId().trim();

    CartItem existing = cartRepo.findByUserIdAndProductId(userId, productId);

    if (existing != null) {
        existing.setQuantity(existing.getQuantity() + item.getQuantity());
        cartRepo.save(existing);
    } else {
        if (item.getQuantity() <= 0) {
            item.setQuantity(1);
        }
        item.setUserId(userId);
        item.setProductId(productId);
        cartRepo.save(item);
    }

    return cartRepo.findByUserId(userId);
}


    @DeleteMapping("/{productId}")
    public void removeItem(@PathVariable String productId, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        cartRepo.deleteByUserIdAndProductId(user.getId(), productId);
    }
}
