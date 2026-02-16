package ecommerce.com.srishakram.Service;


import ecommerce.com.srishakram.Repository.ImageRepository;
import ecommerce.com.srishakram.models.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ImageService {
    @Autowired

    private ImageRepository ImgRepo;

    public Image showimg()
    {
        return ImgRepo.findTopByOrderByIdAsc();
    }

}
