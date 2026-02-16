package ecommerce.com.srishakram.Repository;

import ecommerce.com.srishakram.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image,Long> {
    Image findTopByOrderByIdAsc();
}
