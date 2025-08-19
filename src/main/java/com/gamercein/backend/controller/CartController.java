package com.gamercein.backend.controller;

import com.gamercein.backend.model.CartItem;
import com.gamercein.backend.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = {"http://localhost:3000", "https://e-commerce-frontend-q6t5.vercel.app"}) // adjust if needed
public class CartController {

    private final CartItemRepository cartRepo;

    public CartController(CartItemRepository cartRepo) {
        this.cartRepo = cartRepo;
    }

    @GetMapping("/{userId}")
    public List<CartItem> getCartItems(@PathVariable String userId) {
        return cartRepo.findByUserId(userId);
    }

    @PostMapping
    public CartItem addOrUpdateCartItem(@RequestBody CartItem item) {
        CartItem existing = cartRepo.findByUserIdAndProductId(item.getUserId(), item.getProductId());
        if (existing != null) {
            existing.setQuantity(item.getQuantity());
            return cartRepo.save(existing);
        } else {
            return cartRepo.save(item);
        }
    }

    @DeleteMapping("/{userId}/{productId}")
    public void removeItem(@PathVariable String userId, @PathVariable String productId) {
        cartRepo.deleteByUserIdAndProductId(userId, productId);
    }
}
