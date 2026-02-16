package ecommerce.com.srishakram.Service;

import ecommerce.com.srishakram.Repository.adminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class adminService {

    private final adminRepository adminRepository;

    @Autowired
    public adminService(adminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public String rolecheck(String password) {
        return adminRepository.rolecheck(password);
    }
}
