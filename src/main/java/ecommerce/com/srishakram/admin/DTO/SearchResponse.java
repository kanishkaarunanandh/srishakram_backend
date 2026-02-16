package ecommerce.com.srishakram.admin.DTO;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class SearchResponse {

    private List<String> suggestions;
    private List<ProductDTO> products;
    private List<CollectionDTO> collections;
    private Map<String, List<String>> colorProducts;

}
