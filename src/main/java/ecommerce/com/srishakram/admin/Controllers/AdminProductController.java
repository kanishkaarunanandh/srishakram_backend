package ecommerce.com.srishakram.admin.Controllers;

import ecommerce.com.srishakram.admin.Service.AdminProductService;
import ecommerce.com.srishakram.models.products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/auth")
public class AdminProductController {
    @Autowired
    private AdminProductService productserve;

    @PostMapping("/product")
    public ResponseEntity<products> save(@RequestBody products body) {
        return new ResponseEntity<>(productserve.save(body), HttpStatus.CREATED);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<products> update(@PathVariable Long id, @RequestBody products body) {
        try {
            System.out.println("=== CONTROLLER RECEIVED UPDATE REQUEST ===");
            System.out.println("Product ID: " + id);
            System.out.println("Title: " + body.getTitle());

            // 🔥 Log incoming images
            if (body.getImages() != null) {
                System.out.println("✅ Controller received images count: " + body.getImages().size());
                for (int i = 0; i < body.getImages().size(); i++) {
                    System.out.println("  Image " + (i + 1) + ": " + body.getImages().get(i));
                }
            } else {
                System.out.println("❌ Controller received NO images");
            }
            System.out.println("=========================================");

            products updatedProduct = productserve.updateById(id, body);

            System.out.println("=== CONTROLLER RETURNING RESPONSE ===");
            if (updatedProduct.getImages() != null) {
                System.out.println("✅ Returning images count: " + updatedProduct.getImages().size());
            } else {
                System.out.println("❌ Returning NO images");
            }
            System.out.println("====================================");

            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);

        } catch (RuntimeException e) {
            System.err.println("Product not found: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Error updating product: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}