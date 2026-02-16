package ecommerce.com.srishakram.Repository;

import ecommerce.com.srishakram.models.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<CustomerOrder,Long> {

}
