package ecommerce.com.srishakram.Repository;

import ecommerce.com.srishakram.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface contactRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findByEmail(String email);
}
