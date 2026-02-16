package ecommerce.com.srishakram.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "home_products")
public class products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private String title;
    private String description;
    private Integer offer_price;
    private String img;

    @JdbcTypeCode(org.hibernate.type.SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private List<String> images;

    // Category (plain, silk, pattu, etc.)
    private String category;
    private String subcategory;

    private Boolean instock;
    private Boolean newArrival;

    private Double price;
    private Double blouselength;
    private Double Sareelength;

    private String color;
    private Double weight;
}
