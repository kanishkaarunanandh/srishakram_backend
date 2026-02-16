package ecommerce.com.srishakram.admin.Controllers;

import ecommerce.com.srishakram.admin.Service.AdminImageService;
import ecommerce.com.srishakram.models.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("admin/auth")
public class AdminImageController {

    @Autowired
    private AdminImageService Imgserve;
    @PostMapping("/imgs")
    public ResponseEntity<Image> saveImages(@RequestBody Map<String, String> images) {
        Image image = new Image();
        image.setImg1(images.get("hero"));
        image.setImg2(images.get("section1_1"));
        image.setImg3(images.get("section1_2"));
        image.setImg4(images.get("section1_3"));
        image.setImg5(images.get("section1_4"));
        image.setImg6(images.get("section1_5"));
        image.setImg7(images.get("section2_1"));
        image.setImg8(images.get("section2_2"));
        image.setImg9(images.get("section2_3"));
        image.setImg10(images.get("footer"));
        image.setImg11(images.get("section3_1"));
        image.setImg12(images.get("section3_2"));
        image.setImg13(images.get("section3_3"));

        Image saved = Imgserve.saveOrUpdate(image);
        return ResponseEntity.ok(saved);
    }

}
