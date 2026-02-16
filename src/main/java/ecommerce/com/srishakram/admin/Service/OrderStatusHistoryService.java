package ecommerce.com.srishakram.admin.Service;


import ecommerce.com.srishakram.admin.Repository.CustomerOrderRepository;
import ecommerce.com.srishakram.admin.Repository.OrderStatusHistoryRepository;
import ecommerce.com.srishakram.models.CustomerOrder;
import ecommerce.com.srishakram.models.OrderStatusHistory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderStatusHistoryService {
    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private OrderStatusHistoryRepository orderStatusHistoryRepository;

    @Transactional   // 🔥 Important
    public void updateStatus(Long orderId, String status) {

        // 1️⃣ Fetch Order
        CustomerOrder order = customerOrderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // 2️⃣ Update Main Table (CustomerOrder)
        order.setOrderStatus(status);
        customerOrderRepository.save(order);

        // 3️⃣ Insert into History Table
        OrderStatusHistory history = new OrderStatusHistory();
        history.setCustomerOrder(order);
        history.setStatus(status);
        history.setUpdatedAt(LocalDateTime.now());

        orderStatusHistoryRepository.save(history);
    }

}
