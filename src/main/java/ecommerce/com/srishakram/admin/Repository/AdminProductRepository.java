package ecommerce.com.srishakram.admin.Repository;

import ecommerce.com.srishakram.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminProductRepository extends JpaRepository<Products,Long> {
}
