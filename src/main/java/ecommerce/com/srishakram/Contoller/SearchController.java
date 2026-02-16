package ecommerce.com.srishakram.Contoller;

import ecommerce.com.srishakram.Repository.productRepository;
import ecommerce.com.srishakram.admin.DTO.SearchResponse;
import ecommerce.com.srishakram.admin.Service.SearchService;
import ecommerce.com.srishakram.models.products;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;
    private final productRepository productRepository;

    @GetMapping
    public SearchResponse search(@RequestParam String q) {
        return searchService.search(q);
    }

}
