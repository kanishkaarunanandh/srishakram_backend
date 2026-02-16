package ecommerce.com.srishakram.admin.Repository;

import ecommerce.com.srishakram.models.contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminOrdersRepository extends JpaRepository<contact, Long> {
    Optional<contact> findByEmail(String email);
}
