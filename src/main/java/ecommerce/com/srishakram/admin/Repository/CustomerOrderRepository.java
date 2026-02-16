package ecommerce.com.srishakram.admin.Repository;

import ecommerce.com.srishakram.models.CustomerOrder;
import ecommerce.com.srishakram.models.contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    Optional<CustomerOrder> findByRazorpayOrderId(String razorPayOrderId);
    List<CustomerOrder> findByUser_Id(Long customerId);
    List<CustomerOrder> findByOrderStatusNot(String status);

}