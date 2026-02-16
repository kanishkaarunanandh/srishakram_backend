package ecommerce.com.srishakram.admin.DTO;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class CartRequest {
    private Long id;
    private Long productId;
    private String productName;
    private Long quantity;
    private String image;
    private Double price;
    private Integer offer_price;
}
