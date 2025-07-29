/*package com.gamercein.backend.controller;

import com.gamercein.backend.model.WishlistItem;
import com.gamercein.backend.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin(origins = "*") // adjust if needed
public class WishlistController {

    @Autowired
    private WishlistRepository wishlistRepository;

    @GetMapping("/{userId}")
    public List<WishlistItem> getWishlist(@PathVariable String userId) {
        return wishlistRepository.findByUserId(userId);
    }

    @PostMapping("/api/wishlist")
public ResponseEntity<?> addToWishlist(@RequestBody WishlistItemDto wishlistItem) {
        // Check if exists to avoid duplicates if needed
        return wishlistRepository.save(item);
    }

    @DeleteMapping("/{userId}/{productId}")
    public void removeWishlistItem(@PathVariable String userId, @PathVariable int productId) {
        wishlistRepository.deleteByUserIdAndProductId(userId, productId);
    }
}*/
