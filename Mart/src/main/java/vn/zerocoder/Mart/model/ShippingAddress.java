package vn.zerocoder.Mart.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
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

    @Column(name = "recipient_name")
    private String recipientName; // Tên người nhận

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

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return apartment + ", " + street + ", " + district + ", " + province;
    }
}
