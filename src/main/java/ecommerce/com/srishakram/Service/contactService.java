package ecommerce.com.srishakram.Service;

import ecommerce.com.srishakram.Repository.contactRepository;
import ecommerce.com.srishakram.models.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class contactService {

    @Autowired
    private contactRepository contactrepo;
    public Contact saveContact(Contact contactData)
    {
        return contactrepo.save(contactData);
    }

}
