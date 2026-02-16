package ecommerce.com.srishakram.admin.Service;

import ecommerce.com.srishakram.admin.Repository.AdminImageRepository;
import ecommerce.com.srishakram.models.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminImageService {

    @Autowired
    private AdminImageRepository imgRepo;

    public Image saveOrUpdate(Image incoming) {

        Image existing = imgRepo.findAll()
                .stream()
                .findFirst()
                .orElse(new Image());

        if (incoming.getImg1() != null) existing.setImg1(incoming.getImg1());
        if (incoming.getImg2() != null) existing.setImg2(incoming.getImg2());
        if (incoming.getImg3() != null) existing.setImg3(incoming.getImg3());
        if (incoming.getImg4() != null) existing.setImg4(incoming.getImg4());
        if (incoming.getImg5() != null) existing.setImg5(incoming.getImg5());
        if (incoming.getImg6() != null) existing.setImg6(incoming.getImg6());
        if (incoming.getImg7() != null) existing.setImg7(incoming.getImg7());
        if (incoming.getImg8() != null) existing.setImg8(incoming.getImg8());
        if (incoming.getImg9() != null) existing.setImg9(incoming.getImg9());
        if (incoming.getImg10() != null) existing.setImg10(incoming.getImg10());
        if (incoming.getImg11() != null) existing.setImg11(incoming.getImg11());
        if (incoming.getImg12() != null) existing.setImg12(incoming.getImg12()); // ✅ FIXED
        if (incoming.getImg13() != null) existing.setImg13(incoming.getImg13()); // ✅ FIXED

        return imgRepo.save(existing);
    }
}


