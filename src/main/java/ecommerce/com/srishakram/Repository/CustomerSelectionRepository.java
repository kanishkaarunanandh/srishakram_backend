package ecommerce.com.srishakram.Repository;

import ecommerce.com.srishakram.models.CustomerSelection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerSelectionRepository extends JpaRepository<CustomerSelection,Long> {
    //Optional<CustomerSelection> findByOrderId(Long orderId);
}
