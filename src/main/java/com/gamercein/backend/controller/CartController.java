package com.gamercein.backend.controller;

import com.gamercein.backend.model.CartItem;
import com.gamercein.backend.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*") // adjust if needed
public class CartController {

    private final CartItemRepository cartRepo;

    public CartController(CartItemRepository cartRepo) {
        this.cartRepo = cartRepo;
    }

   @GetMapping
public List<CartItem> getCartItems(Authentication authentication) {
    String username = authentication.getName();
    User user = userRepository.findByUsername(username);
    return cartRepo.findByUserId(user.getId());
}
   @PostMapping
public CartItem addOrUpdateCartItem(@RequestBody CartItem item, Authentication authentication) {
    String username = authentication.getName();
    User user = userRepository.findByUsername(username);
    item.setUserId(user.getId());
    if (item.getProductId() == null || item.getUserId() == null) {
        throw new IllegalArgumentException("Product ID and User ID are required");
    }

    // Ensure IDs are stored as strings
    String productId = item.getProductId().trim();
    String userId = item.getUserId().trim();

    CartItem existing = cartRepo.findByUserIdAndProductId(userId, productId);

    if (existing != null) {
        existing.setQuantity(existing.getQuantity() + item.getQuantity());
        return cartRepo.save(existing);
    } else {
        if (item.getQuantity() <= 0) {
            item.setQuantity(1);
        }
        item.setUserId(userId);
        item.setProductId(productId);
        return cartRepo.save(item);
    }
}


    @DeleteMapping("/{userId}/{productId}")
    public void removeItem(@PathVariable String userId, @PathVariable String productId) {
        cartRepo.deleteByUserIdAndProductId(userId, productId);
    }
}
