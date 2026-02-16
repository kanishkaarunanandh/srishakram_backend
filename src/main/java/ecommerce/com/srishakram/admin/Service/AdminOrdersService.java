package ecommerce.com.srishakram.admin.Service;


import ecommerce.com.srishakram.Repository.contactRepository;
import ecommerce.com.srishakram.admin.Repository.AdminOrdersRepository;
import ecommerce.com.srishakram.models.contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminOrdersService {
    @Autowired
    private AdminOrdersRepository contactrepo;
    public List<contact> show()
    {
        return contactrepo.findAll();
    }

}
