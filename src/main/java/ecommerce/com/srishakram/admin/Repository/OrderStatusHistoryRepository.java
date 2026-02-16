package ecommerce.com.srishakram.admin.Repository;

import ecommerce.com.srishakram.models.OrderStatusHistory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory,Long> {
    List<OrderStatusHistory> findByCustomerOrderOrderIdOrderByUpdatedAtAsc(Long id);

    // Delete all status history records for a specific order (needed for order deletion)
    @Modifying
    @Transactional
    @Query("DELETE FROM OrderStatusHistory osh WHERE osh.customerOrder.orderId = :orderId")
    void deleteByOrderId(@Param("orderId") Long orderId);

}
