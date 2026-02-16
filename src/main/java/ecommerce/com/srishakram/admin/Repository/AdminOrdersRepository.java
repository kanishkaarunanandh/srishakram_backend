package ecommerce.com.srishakram.admin.Repository;

import ecommerce.com.srishakram.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminOrdersRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findByEmail(String email);
}
