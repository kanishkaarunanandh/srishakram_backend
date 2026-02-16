package ecommerce.com.srishakram.Contoller;


import ecommerce.com.srishakram.Repository.productRepository;
import ecommerce.com.srishakram.Service.productService;
import ecommerce.com.srishakram.models.products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/upload")
public class productController {
  @Autowired
    private productService productserve;
  @Autowired
   private productRepository productRepository;

    @GetMapping("/recent")
    public ResponseEntity<List<products>> getRecentProducts() {
        return new ResponseEntity<>(
                productserve.getRecentProducts(),
                HttpStatus.OK
        );
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<products> getProducts(@PathVariable Long id) {
        return new ResponseEntity<>(
                productserve.getProducts(id),
                HttpStatus.OK
        );
    }
    @GetMapping("/getproduct/category")
    public ResponseEntity<Map<String, Object>> getProducts(
            @RequestParam String category,
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice,
            @RequestParam(required = false) Boolean inStock,
            @RequestParam(required = false) Boolean newArrival,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) List<String> color,
            @RequestParam(required = false) String subcategory
    ) {
        Page<products> pageData =
                productserve.getProductscategory(
                        category,
                        minPrice,
                        maxPrice,
                        inStock,
                        newArrival,
                        page,
                        color,
                        subcategory
                );

        Map<String, Object> res = new HashMap<>();
        res.put("content", pageData.getContent());
        res.put("totalPages", pageData.getTotalPages());
        res.put("totalElements", pageData.getTotalElements());

        res.put("inStockCount",
                productserve.countInStock(category, minPrice, maxPrice, color));
        res.put("newArrivalCount",
                productserve.countNewArrivals(category, minPrice, maxPrice, color));


        return ResponseEntity.ok(res);
    }

    @GetMapping("getproduct/all")
    public ResponseEntity<List<products>> showallproduct()
    {
        return new ResponseEntity<>(productserve.showallproduct(),HttpStatus.OK);
    }

    // Search by title
    @GetMapping("/search")
    public ResponseEntity<List<products>> searchProductsByTitle(@RequestParam String title) {
        List<products> result = productserve.searchByTitle(title);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/getproduct/colors")
    public List<String> getColorsByCategory(@RequestParam String category) {
        return productRepository.findDistinctColorsByCategory(category);
    }
    @GetMapping("/search/category-or-subcategory")
    public ResponseEntity<Map<String, Object>> searchByCategoryOrSubcategory(
            @RequestParam String value,
            @RequestParam(defaultValue = "0") int page
    ) {
        Page<products> pageData =
                productserve.searchByCategoryOrSubcategory(value, page);

        Map<String, Object> res = new HashMap<>();
        res.put("content", pageData.getContent());
        res.put("totalPages", pageData.getTotalPages());
        res.put("totalElements", pageData.getTotalElements());

        return ResponseEntity.ok(res);
    }


}
