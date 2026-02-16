package ecommerce.com.srishakram.admin.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.com.srishakram.models.Catelog;

import java.util.List;

public interface CatelogRepository extends JpaRepository<Catelog,Long> {

    List<Catelog> findAllByOrderByIdAsc();

}
