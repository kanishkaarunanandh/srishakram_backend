package ecommerce.com.srishakram.admin.DTO;

import lombok.Data;

import java.util.List;

@Data
public class orderCreated {
    private String orderNo;

    public orderCreated(String orderNo) {
        this.orderNo = orderNo;
    }
}
