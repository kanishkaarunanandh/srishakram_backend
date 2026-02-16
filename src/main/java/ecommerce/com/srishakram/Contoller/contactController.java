package ecommerce.com.srishakram.Contoller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ecommerce.com.srishakram.Service.contactService;
import ecommerce.com.srishakram.models.contact;

import java.util.List;

@RestController
public class contactController {

    @Autowired
    private contactService  contactService;

    @PostMapping("/contact/save")
    public ResponseEntity<contact> saveContact(@RequestBody contact contactData)
    {

        return new ResponseEntity<>(contactService.saveContact(contactData), HttpStatus.CREATED) ;
    }

}
