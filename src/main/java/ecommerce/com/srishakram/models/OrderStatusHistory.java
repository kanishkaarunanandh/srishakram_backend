package ecommerce.com.srishakram.models;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class OrderStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private CustomerOrder customerOrder;
}

