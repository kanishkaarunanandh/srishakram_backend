package ecommerce.com.srishakram.admin.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SareeJournalRequest {
    private String productName;

    private String heroImage;
    private List<HeritageStepDTO> heritageSteps;
    private String zariCertificateImage;
    private List<customerReviewDTO> customerReview;
}

