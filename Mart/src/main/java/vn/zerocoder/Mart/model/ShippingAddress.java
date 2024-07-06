package vn.zerocoder.Mart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_shipping_address")
public class ShippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "province")
    private String province; // Tỉnh/Thành phố

    @Column(name = "district")
    private String district; // Quận/Huyện

    @Column(name = "street")
    private String street; // Đường

    @Column(name = "apartment")
    private String apartment; // Số nhà

    @Column(name = "phone_number")
    private String phoneNumber; // Số điện thoại

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "user_id")
    private User user;
}
