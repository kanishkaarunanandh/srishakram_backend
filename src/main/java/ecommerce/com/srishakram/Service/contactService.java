package ecommerce.com.srishakram.Service;

import ecommerce.com.srishakram.Repository.contactRepository;
import ecommerce.com.srishakram.models.contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class contactService {

    @Autowired
    private contactRepository contactrepo;
    public contact saveContact(contact contactData)
    {
        return contactrepo.save(contactData);
    }

}
