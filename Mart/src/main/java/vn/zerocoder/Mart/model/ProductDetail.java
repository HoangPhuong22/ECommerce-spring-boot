package vn.zerocoder.Mart.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_product_detail")
public class ProductDetail extends BaseEntity{

    @Column(name = "sku")
    private String sku;

    @Column(name = "qty")
    private Integer qty;

    @Column(name = "product_image")
    private String productImage;

    @Column(name = "price")
    private Long price;

    @ManyToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    }, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    }, fetch = FetchType.LAZY)
    @JoinTable(
            name = "tbl_product_configuration",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "variation_option_id")
    )
    private List<VariationOption> options;

    @PrePersist
    @PreUpdate
    private void updateQuantity() {
        this.product.setQuantity(this.product.getQuantity() + this.qty);
    }
}
