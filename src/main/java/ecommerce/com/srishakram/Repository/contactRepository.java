package ecommerce.com.srishakram.Repository;

import ecommerce.com.srishakram.models.contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface contactRepository extends JpaRepository<contact, Long> {
    Optional<contact> findByEmail(String email);
}
