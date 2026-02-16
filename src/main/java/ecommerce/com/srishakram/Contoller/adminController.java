package ecommerce.com.srishakram.Contoller;

import ecommerce.com.srishakram.Service.adminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;

@RestController
public class adminController {

    private final adminService adminService;

    @Autowired
    public adminController(adminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> adminLogin(@RequestBody Map<String, String> payload) {
        String password = payload.get("password");
        String role = adminService.rolecheck(password);
        if (role != null) {
            return ResponseEntity.ok(role);
        } else {
            return ResponseEntity.badRequest().body("Invalid password");
        }
    }

}
