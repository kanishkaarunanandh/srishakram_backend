package ecommerce.com.srishakram.admin.Repository;

import ecommerce.com.srishakram.models.SareeJournal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SareeJournalRepository
        extends JpaRepository<SareeJournal, Long> {
    List<SareeJournal> findTop3ByOrderByIdDesc();
}