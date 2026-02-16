package ecommerce.com.srishakram.admin.Controllers;


import ecommerce.com.srishakram.admin.Service.AdminOrdersService;
import ecommerce.com.srishakram.models.contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequestMapping("admin/auth")
@RestController
public class AdminOrdersController {

    @Autowired
    private AdminOrdersService contactService;

    @GetMapping("/getorders")
    public ResponseEntity<List<contact>> show()
    {
        return new ResponseEntity<>(contactService.show(), HttpStatus.OK);
    }
}
