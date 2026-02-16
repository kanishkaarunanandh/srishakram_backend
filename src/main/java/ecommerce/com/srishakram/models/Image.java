package ecommerce.com.srishakram.models;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@Data
@Entity
public class Image {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;
    String img1;
    String img2;
    String img3;
    String img4;
    String img5;
    String img6;
    String img7;
    String img8;
    String img9;
    String img10;
    String img11;
    String img12;
    String img13;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

}
