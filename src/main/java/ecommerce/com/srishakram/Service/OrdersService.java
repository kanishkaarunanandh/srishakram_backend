package ecommerce.com.srishakram.Service;

import ecommerce.com.srishakram.Repository.OrdersRepository;
import ecommerce.com.srishakram.models.CustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;

    public List<CustomerOrder> getAllOrders() {
        return ordersRepository.findAll();
    }

    public CustomerOrder getOrderById(Long id) {
        return ordersRepository.findById(id).orElse(null);
    }

}
