package ecommerce.com.srishakram.admin.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Payment;
import ecommerce.com.srishakram.admin.Repository.CustomerOrderRepository;
import ecommerce.com.srishakram.models.CartItem;
import ecommerce.com.srishakram.models.CustomerOrder;
import ecommerce.com.srishakram.models.Users;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.List;
import java.util.Map;

@Service
public class CustomerOrderService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerOrderService.class);

    @Autowired
    private CustomerOrderRepository customerOrderRepo;

    private RazorpayClient client;

    @Value("${razorpay.key.id}")
    private String razorPayKey;

    @Value("${razorpay.secret.key}")
    private String razorPaySecret;

    @Transactional
    public CustomerOrder createOrder(CustomerOrder customerOrder, Users loggedInUser) throws Exception {

        customerOrder.setUser(loggedInUser);

        List<CartItem> mappedItems = customerOrder.getItems().stream().map(dto -> {
            CartItem item = new CartItem();
            item.setProductId(dto.getProductId());
            item.setProductName(dto.getProductName());
            item.setQuantity(dto.getQuantity());
            item.setImage(dto.getImage());
            item.setPrice(dto.getPrice());
            item.setOffer_price(dto.getOffer_price());
            return item;
        }).toList();

        customerOrder.setItems(mappedItems);

        if ("COD".equalsIgnoreCase(customerOrder.getPaymentMethod())) {
            customerOrder.setPaymentMethod("COD");
            customerOrder.setOrderStatus("COD");
            customerOrder.setRazorpayOrderId(null);
        } else {
            JSONObject orderReq = new JSONObject();
            orderReq.put("amount", customerOrder.getAmount() * 100);
            orderReq.put("currency", "INR");
            orderReq.put("receipt", customerOrder.getEmail());

            this.client = new RazorpayClient(razorPayKey, razorPaySecret);
            Order razorPayOrder = client.orders.create(orderReq);

            customerOrder.setPaymentMethod("razorpay");
            customerOrder.setRazorpayOrderId(razorPayOrder.get("id"));
            customerOrder.setOrderStatus(razorPayOrder.get("status")); // "created"
        }

        return customerOrderRepo.save(customerOrder);
    }

    @Transactional
    public CustomerOrder updateOrder(Map<String, String> responsePayLoad) {

        String razorPayOrderId = responsePayLoad.get("razorpay_order_id");
        String razorpayPaymentId = responsePayLoad.get("razorpay_payment_id");
        String razorpaySignature = responsePayLoad.get("razorpay_signature");

        CustomerOrder order = customerOrderRepo
                .findByRazorpayOrderId(razorPayOrderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + razorPayOrderId));

        if ("PAID".equals(order.getOrderStatus())) {
            return order;
        }

        if (razorpayPaymentId == null || razorpayPaymentId.isEmpty()) {
            order.setOrderStatus("FAILED");
            return customerOrderRepo.save(order);
        }

        if (!verifySignature(razorPayOrderId, razorpayPaymentId, razorpaySignature)) {
            order.setOrderStatus("FAILED");
            return customerOrderRepo.save(order);
        }

        try {
            if (client == null) {
                client = new RazorpayClient(razorPayKey, razorPaySecret);
            }

            Payment payment = client.payments.fetch(razorpayPaymentId);

            if (!"captured".equals(payment.get("status"))) {
                order.setOrderStatus("FAILED");
                return customerOrderRepo.save(order);
            }
        } catch (Exception e) {
            logger.error("Razorpay fetch error", e);
            order.setOrderStatus("FAILED");
            return customerOrderRepo.save(order);
        }

        order.setOrderStatus("PAID");
        order.setPaymentId(razorpayPaymentId);
        customerOrderRepo.save(order);

        return order;
    }

    @Transactional
    public void reconcilePendingPayments() {
        List<CustomerOrder> pendingOrders = customerOrderRepo.findByOrderStatusNot("PAID");

        for (CustomerOrder order : pendingOrders) {
            try {
                if (order.getPaymentId() != null) {
                    Payment payment = client.payments.fetch(order.getPaymentId());
                    if ("captured".equals(payment.get("status"))) {
                        order.setOrderStatus("PAID");
                        customerOrderRepo.save(order);
                    }
                }
            } catch (Exception e) {
                logger.error("Reconciliation failed for order: " + order.getOrderId(), e);
            }
        }
    }

    @Transactional
    public CustomerOrder cancelPaidOrder(CustomerOrder order) {
        if ("PAID".equals(order.getOrderStatus()) && order.getPaymentId() != null) {
            try {
                JSONObject refundRequest = new JSONObject();
                refundRequest.put("payment_id", order.getPaymentId());
                client.payments.refund(order.getPaymentId(), refundRequest);
            } catch (Exception e) {
                logger.error("Refund failed for order: " + order.getOrderId(), e);
                throw new RuntimeException("Refund failed");
            }
        }

        order.setOrderStatus("CANCELLED");
        customerOrderRepo.save(order);

        return order;
    }

    private boolean verifySignature(String orderId, String paymentId, String signature) {
        try {
            String payload = orderId + "|" + paymentId;
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(razorPaySecret.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);

            byte[] hash = mac.doFinal(payload.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString().equals(signature);
        } catch (Exception e) {
            logger.error("Error verifying signature", e);
            return false;
        }
    }
}
