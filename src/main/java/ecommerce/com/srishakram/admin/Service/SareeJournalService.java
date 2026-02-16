package ecommerce.com.srishakram.admin.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ecommerce.com.srishakram.admin.DTO.SareeJournalRequest;
import ecommerce.com.srishakram.admin.Repository.SareeJournalRepository;
import ecommerce.com.srishakram.models.SareeJournal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SareeJournalService {

    private final SareeJournalRepository repository;
    private final ObjectMapper objectMapper;

    public SareeJournal save(SareeJournalRequest request) {

        SareeJournal journal = new SareeJournal();
        journal.setHeroImage(request.getHeroImage());
        journal.setZariCertificateImage(request.getZariCertificateImage());
        journal.setProductName(request.getProductName());
        journal.setHeritageSteps(
                objectMapper.valueToTree(request.getHeritageSteps())
        );

        journal.setCustomerReview(
                objectMapper.valueToTree(request.getCustomerReview())
        );
        return repository.save(journal);
    }
    public SareeJournal getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<SareeJournal> getRecent()
    {
        return repository.findTop3ByOrderByIdDesc();
    }
    public List<SareeJournal> getAlljournal()
    {
        return repository.findAll();
    }
}
