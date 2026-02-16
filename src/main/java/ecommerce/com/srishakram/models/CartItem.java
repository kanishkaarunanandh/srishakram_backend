package ecommerce.com.srishakram.models;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class CartItem {
    private Long productId;
    private String productName;
    private Long quantity;
    private String image;
    private Double price;
    private Integer offer_price;
}
