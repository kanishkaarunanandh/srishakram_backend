package ecommerce.com.srishakram.admin.Repository;

import ecommerce.com.srishakram.models.products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminProductRepository extends JpaRepository<products,Long> {
}
