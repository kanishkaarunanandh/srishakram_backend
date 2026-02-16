package ecommerce.com.srishakram.admin.Controllers;


import ecommerce.com.srishakram.admin.Repository.CustomerOrderRepository;
import ecommerce.com.srishakram.admin.Repository.OrderStatusHistoryRepository;
import ecommerce.com.srishakram.admin.Service.OrderStatusHistoryService;
import ecommerce.com.srishakram.models.CustomerOrder;
import ecommerce.com.srishakram.models.OrderStatusHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class OrderStatusHistoryController {

    @Autowired
    private OrderStatusHistoryRepository orderStatusHistoryRepository;
    @Autowired
    private OrderStatusHistoryService orderStatusHistoryService;
    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @GetMapping("/orders/customer/{userId}")
    public List<CustomerOrder> getOrdersByCustomerId(@PathVariable Long userId) {
        return customerOrderRepository.findByUser_Id(userId);
    }
    @GetMapping("/orders/{id}/tracking")
    public List<OrderStatusHistory> getTracking(@PathVariable Long id) {
        return orderStatusHistoryRepository
                .findByCustomerOrderOrderIdOrderByUpdatedAtAsc(id);
    }

    @PutMapping("/orders/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {

        String status = request.get("status");

        orderStatusHistoryService.updateStatus(id, status);

        return ResponseEntity.ok("Order status updated successfully");
    }


}
