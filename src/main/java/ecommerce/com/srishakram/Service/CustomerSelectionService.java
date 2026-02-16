package ecommerce.com.srishakram.Service;


import ecommerce.com.srishakram.Repository.CustomerSelectionRepository;
import ecommerce.com.srishakram.models.CustomerSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerSelectionService {

    @Autowired
    private CustomerSelectionRepository CSrepo;

    public CustomerSelection saveSelection(CustomerSelection selection) {
        return CSrepo.save(selection);
    }

//    public CustomerSelection getByOrderId(Long orderId) {
//        return CSrepo.findByOrderId(orderId).orElse(null);
//    }

}
