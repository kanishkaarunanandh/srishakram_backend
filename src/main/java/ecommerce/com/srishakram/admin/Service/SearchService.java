package ecommerce.com.srishakram.admin.Service;

import ecommerce.com.srishakram.Repository.productRepository;
import ecommerce.com.srishakram.admin.DTO.ProductDTO;
import ecommerce.com.srishakram.admin.DTO.SearchResponse;
import ecommerce.com.srishakram.models.products;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SearchService {

    @Autowired
    private final productRepository productRepo;
   // private final CollectionRepository collectionRepo;

    public SearchResponse search(String q) {
        SearchResponse response = new SearchResponse();

        // 1️⃣ Products matching title/category or color
        List<products> matchedProducts = productRepo.searchMainProducts(q); // title/category
        List<products> colorMatched = productRepo.searchEverywhere(q); // exact color match

        // Merge both lists (avoid duplicates)
        List<products> combinedProducts = Stream.concat(matchedProducts.stream(), colorMatched.stream())
                .distinct()
                .limit(3)  // limit for UI
                .toList();

        // Map to DTO
        List<ProductDTO> productDTOs = combinedProducts.stream()
                .map(p -> {
                    ProductDTO dto = new ProductDTO();
                    dto.setId(p.getId());
                    dto.setName(p.getTitle());
                    dto.setImageUrl(p.getImg());
                    dto.setColor(p.getColor());
                    return dto;
                })
                .toList();

        // 2️⃣ Suggestions (from product names)
        List<String> suggestions = productDTOs.stream()
                .map(p -> p.getName().toLowerCase())
                .distinct()
                .limit(5)
                .toList();

        // 3️⃣ Color Products
        Map<String, List<String>> colorProducts =
                combinedProducts.stream()
                        .filter(p -> p.getColor() != null && p.getCategory() != null)
                        .collect(Collectors.groupingBy(
                                products::getCategory,
                                Collectors.mapping(
                                        products::getColor,
                                        Collectors.collectingAndThen(
                                                Collectors.toCollection(LinkedHashSet::new),
                                                ArrayList::new
                                        )
                                )
                        ));


        response.setProducts(productDTOs);
        response.setSuggestions(suggestions);
        response.setColorProducts(colorProducts);

        return response;
    }

}

