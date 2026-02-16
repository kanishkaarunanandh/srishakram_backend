package ecommerce.com.srishakram.admin.Service;

import ecommerce.com.srishakram.admin.Repository.AdminOrdersRepository;
import ecommerce.com.srishakram.admin.Repository.UsersRepository;
import ecommerce.com.srishakram.models.Users;
import ecommerce.com.srishakram.models.contact;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
   @Autowired
    private UsersRepository usersrepo;
    @Autowired
    private AdminOrdersRepository adminorder;

    public Users createUsers(Users user) {
        return usersrepo.save(user);
    }

    @Transactional
    public boolean toggleCustomByEmail(String email) {

        // 1️⃣ Contact table check
        contact contact = adminorder.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        // 2️⃣ Login table check
        usersrepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not registered")
                );

        // 3️⃣ TOGGLE
        boolean newValue = !contact.isCustom();
        contact.setCustom(newValue);
        adminorder.save(contact);

        return newValue; // send back to frontend
    }
    public Users findByEmail(String email) {
        return usersrepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

}
