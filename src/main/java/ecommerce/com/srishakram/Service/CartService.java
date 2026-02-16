package ecommerce.com.srishakram.Service;

import ecommerce.com.srishakram.Repository.CartRepository;
import ecommerce.com.srishakram.Repository.productRepository;
import ecommerce.com.srishakram.admin.DTO.CartRequest;
import ecommerce.com.srishakram.admin.Repository.UsersRepository;
import ecommerce.com.srishakram.models.Cart;
import ecommerce.com.srishakram.models.Users;
import ecommerce.com.srishakram.models.products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private productRepository productRepo;

    @Autowired
    private UsersRepository userRepo;

    public Cart addToCart(CartRequest req, UserDetails userDetails) {

        Users user = userRepo.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        products product = productRepo.findById(req.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = cartRepo
                .findByUsersAndProduct(user, product)
                .orElse(new Cart());

        cart.setUsers(user);
        cart.setProduct(product);

        cart.setQuantity(
                cart.getQuantity() == null
                        ? req.getQuantity()
                        : cart.getQuantity() + req.getQuantity()
        );

        return cartRepo.save(cart);
    }

    public List<CartRequest> getUserCart(UserDetails userDetails) {
        Users user = userRepo.findByEmail(userDetails.getUsername())
                .orElseThrow();
        return cartRepo.findByUsers(user).stream().map(cart -> {
            CartRequest dto = new CartRequest();
            dto.setId(cart.getId());
            dto.setProductId(cart.getProduct().getId());
            dto.setProductName(cart.getProduct().getTitle());
            dto.setQuantity(cart.getQuantity());
            dto.setImage(cart.getProduct().getImg());
            dto.setPrice(cart.getProduct().getPrice());
            dto.setOffer_price(cart.getProduct().getOffer_price());
            return dto;
        }).toList();
    }


    public CartRequest updateQuantity(Long id, Long qty, UserDetails userDetails) {
        Cart cart = getUserCartItem(id, userDetails);
        cart.setQuantity(qty);
        cart = cartRepo.save(cart);

        CartRequest dto = new CartRequest();
        dto.setId(cart.getId());
        dto.setProductId(cart.getProduct().getId());
        dto.setProductName(cart.getProduct().getTitle());
        dto.setQuantity(cart.getQuantity());
        dto.setImage(cart.getProduct().getImg());
        dto.setPrice(cart.getProduct().getPrice());
        dto.setOffer_price(cart.getProduct().getOffer_price());
        return dto;
    }

    public CartRequest delete(Long id, UserDetails userDetails) {
        Cart cart = getUserCartItem(id, userDetails);

        // Map to DTO before deleting
        CartRequest dto = new CartRequest();
        dto.setId(cart.getId());
        dto.setProductId(cart.getProduct().getId());
        dto.setProductName(cart.getProduct().getTitle());
        dto.setQuantity(cart.getQuantity());
        dto.setImage(cart.getProduct().getImg());
        dto.setPrice(cart.getProduct().getPrice());
        dto.setOffer_price(cart.getProduct().getOffer_price());

        cartRepo.delete(cart);
        return dto;
    }


    private Cart getUserCartItem(Long id, UserDetails userDetails) {
        Users user = userRepo.findByEmail(userDetails.getUsername())
                .orElseThrow();
        return cartRepo.findByIdAndUsers(id, user)
                .orElseThrow(() -> new RuntimeException("Not authorized"));
    }
    public void clearUserCart(UserDetails userDetails) {
        Users user = userRepo.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Cart> userCart = cartRepo.findByUsers(user);
        cartRepo.deleteAll(userCart);
    }

}
