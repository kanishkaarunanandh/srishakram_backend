package ecommerce.com.srishakram.Contoller;


import ecommerce.com.srishakram.Service.ImageService;
import ecommerce.com.srishakram.models.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/upload")
public class ImageController {
    @Autowired
    private ImageService Imgserve;
    @GetMapping("/display")
    public ResponseEntity<Image>displayimg()
    {
        return new ResponseEntity<>(Imgserve.showimg(),HttpStatus.OK);
    }
}
