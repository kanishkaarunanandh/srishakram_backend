package ecommerce.com.srishakram.admin.Service;

import ecommerce.com.srishakram.admin.Repository.CatelogRepository;
import ecommerce.com.srishakram.models.Catelog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatelogService {

    @Autowired
    private CatelogRepository Catelogrepo;

    public Catelog save(Catelog body)
    {
        return Catelogrepo.save(body);
    }
}
