package ecommerce.com.srishakram.Contoller;


import ecommerce.com.srishakram.Service.CustomerSelectionService;
import ecommerce.com.srishakram.models.CustomerSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/custom")
public class CustomerSelectionController {

    @Autowired
    private CustomerSelectionService selectionService;
    @PostMapping("/colors")
    public CustomerSelection saveSelection(@RequestBody CustomerSelection selection) {
        return selectionService.saveSelection(selection);
    }

//    @GetMapping("/{orderId}")
//    public CustomerSelection getCustomerSelection(@PathVariable Long orderId) {
//        return selectionService.getByOrderId(orderId);
//    }
}
