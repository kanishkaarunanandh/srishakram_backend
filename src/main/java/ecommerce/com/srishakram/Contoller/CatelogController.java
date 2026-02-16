package ecommerce.com.srishakram.Contoller;

import ecommerce.com.srishakram.admin.Repository.CatelogRepository;
import ecommerce.com.srishakram.admin.Service.CatelogService;
import ecommerce.com.srishakram.models.Catelog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
public class CatelogController {

    @Autowired
    private CatelogService CatelogService;
    @Autowired
    private CatelogRepository catelogRepository;

    @PostMapping("/admin/auth/catelog")
    public ResponseEntity<Catelog> savecatelog(@RequestBody Catelog body)
    {
        return new ResponseEntity<>(CatelogService.save(body), HttpStatus.CREATED);
    }

    @PostMapping("/admin/auth/catelog/add-subcategory")
    public ResponseEntity<?> addSubcategory(@RequestBody Map<String,String> body) {

        Long id = Long.valueOf(body.get("catelogId"));
        String sub = body.get("subcategory");

        Catelog c = catelogRepository.findById(id).orElseThrow();

        c.getSubcategory().add(sub);

        catelogRepository.save(c);

        return ResponseEntity.ok().build();
    }
    @GetMapping("/catelog")
    public List<Catelog> getCatalog() {
        return catelogRepository.findAllByOrderByIdAsc();
    }


}
