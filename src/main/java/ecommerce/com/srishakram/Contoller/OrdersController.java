package ecommerce.com.srishakram.Contoller;

import ecommerce.com.srishakram.Service.OrdersService;
import ecommerce.com.srishakram.models.CustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping
    public List<CustomerOrder> getAllOrders() {
        return ordersService.getAllOrders();
    }

    @GetMapping("/{id}")
    public CustomerOrder getOrderDetails(@PathVariable Long id) {
        return ordersService.getOrderById(id);
    }


}
