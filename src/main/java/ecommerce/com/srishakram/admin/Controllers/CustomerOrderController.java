package ecommerce.com.srishakram.admin.Controllers;

import ecommerce.com.srishakram.admin.Repository.CustomerOrderRepository;
import ecommerce.com.srishakram.admin.Repository.OrderStatusHistoryRepository;
import ecommerce.com.srishakram.admin.Service.CustomerOrderService;
import ecommerce.com.srishakram.admin.Service.UsersService;
import ecommerce.com.srishakram.models.CustomerOrder;
import ecommerce.com.srishakram.models.Users;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class CustomerOrderController {

    @Autowired
    private CustomerOrderService customerOrderService;
    @Autowired
    private UsersService userService;
    @Autowired
    private CustomerOrderRepository customerOrderRepository;
    @Autowired
    private OrderStatusHistoryRepository orderStatusHistoryRepository;

    @PostMapping("/create-order")
    public ResponseEntity<CustomerOrder> createOrder(@RequestBody CustomerOrder customerOrder) {
        try {
            Users loggedInUser = userService.findByEmail(customerOrder.getEmail());
            if (loggedInUser == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            // Set order status based on payment method
            if ("COD".equalsIgnoreCase(customerOrder.getPaymentMethod())) {
                customerOrder.setOrderStatus("COD"); // directly mark as COD
                customerOrder.setRazorpayOrderId(null); // no Razorpay order
            } else {
                customerOrder.setOrderStatus("created"); // default for online payments
            }

            CustomerOrder createdOrder = customerOrderService.createOrder(customerOrder, loggedInUser);

            // Only for online payment: create Razorpay order and update razorpayOrderId
            if (!"COD".equalsIgnoreCase(customerOrder.getPaymentMethod())) {
                // call Razorpay and set razorpayOrderId here
                // customerOrder.setRazorpayOrderId(razorpayOrder.getId());
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




    @PostMapping("/handle-payment-callback")
    public ResponseEntity<Map<String, Object>> handlePaymentCallback(@RequestBody Map<String, String> payload) {
        try {
            String razorpayPaymentId = payload.get("razorpay_payment_id");
            String razorpayOrderId = payload.get("razorpay_order_id");

            if (razorpayPaymentId == null || razorpayOrderId == null) {
                throw new RuntimeException("Invalid payment payload");
            }

            // Use the existing updateOrder method
            CustomerOrder updatedOrder = customerOrderService.updateOrder(payload);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Payment verified successfully",
                    "orderId", updatedOrder.getOrderId(),
                    "status", updatedOrder.getOrderStatus()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }


    @DeleteMapping("/orders/{id}")
    @Transactional
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            // Check if order exists
            if (!customerOrderRepository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Order not found with ID: " + id);
            }

            // Get the order first
            CustomerOrder order = customerOrderRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            // Step 1: Delete all order status history records for this order
            orderStatusHistoryRepository.deleteByOrderId(id);

            // Step 2: Clear order items to avoid foreign key constraint issues
            if (order.getItems() != null) {
                order.getItems().clear();
            }

            // Step 3: Save the order to persist the cleared relationships
            customerOrderRepository.save(order);

            // Step 4: Now delete the order
            customerOrderRepository.delete(order);

            return ResponseEntity.ok("Order deleted successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete order: " + e.getMessage());
        }
    }



}