package ecommerce.com.srishakram.admin.DTO;

import lombok.Data;

@Data
public class OrderItemDto {
    private String name;
    private Integer quantity;
    private String image;
    private Double price;
    private Long productId;
}
