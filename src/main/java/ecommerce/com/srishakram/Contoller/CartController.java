package ecommerce.com.srishakram.Contoller;

import ecommerce.com.srishakram.Service.CartService;
import ecommerce.com.srishakram.admin.DTO.CartRequest;
import ecommerce.com.srishakram.models.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<Cart> addToCart(
            @RequestBody CartRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return new ResponseEntity<>(
                cartService.addToCart(request, userDetails),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public List<CartRequest> getUserCart(@AuthenticationPrincipal UserDetails userDetails) {
        return cartService.getUserCart(userDetails);
    }


    @PutMapping("/{id}/{quantity}")
    public CartRequest updateQuantity(
            @PathVariable Long id,
            @PathVariable Long quantity,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return cartService.updateQuantity(id, quantity, userDetails);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<CartRequest> deleteItem(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        CartRequest deleted = cartService.delete(id, userDetails);
        return ResponseEntity.ok(deleted);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        cartService.clearUserCart(userDetails);
        return ResponseEntity.ok("Cart cleared");
    }




}
