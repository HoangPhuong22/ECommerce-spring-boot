package vn.zerocoder.Mart.model;

import jakarta.persistence.*;
import lombok.*;
import vn.zerocoder.Mart.enums.ProductStatus;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_product")
public class Product extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "product_image")
    private String productImage;

    @Column(name = "price")
    private Long price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name ="status")
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(name = "promotion_rate")
    private Integer promotionRate;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<ProductDetail> productDetails;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.DETACH,
                    CascadeType.REFRESH
            })
    @JoinColumn(name = "brand_id")
    private Brand brand;


    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.DETACH,
                    CascadeType.REFRESH
            })
    @JoinColumn(name = "category_id")
    private Category category;

}
