package ecommerce.com.srishakram.admin.DTO;

import lombok.Data;

import java.util.List;

@Data
public class createOrderRequest {
    private List<OrderItemDto> orderItems;
}
