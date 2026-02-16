package ecommerce.com.srishakram.admin.Controllers;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/payment")
@CrossOrigin
public class PaymentController {

    private RazorpayClient razorpayClient;

    private final String KEY_ID = "rzp_test_SBY90j69ONO15o";
    private final String KEY_SECRET = "tRyFLOEwPTJQNB42DFDAkJtf"; // replace with your secret

    public PaymentController() throws RazorpayException {
        razorpayClient = new RazorpayClient(KEY_ID, KEY_SECRET);
    }

    @PostMapping("/create-order")
    public String createOrder(@RequestBody Map<String, Integer> data) throws RazorpayException {
        int amount = data.get("amount") * 100; // in paise

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "order_rcptid_11");
        orderRequest.put("payment_capture", true); // auto capture

        Order order = razorpayClient.orders.create(orderRequest);
        return order.toString();
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> data) {
        try {
            String orderId = data.get("razorpay_order_id");
            String paymentId = data.get("razorpay_payment_id");
            String signature = data.get("razorpay_signature");

            // Verify signature
            String payload = orderId + "|" + paymentId;
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(KEY_SECRET.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            String generatedSignature = Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(payload.getBytes()));

            // Check if signature matches
            if (signature.equals(generatedSignature)) {
                // Save order to DB here if needed
                return ResponseEntity.ok("Payment verified successfully!");
            } else {
                return ResponseEntity.badRequest().body("Invalid signature");
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error verifying payment");
        }
    }
}
