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
@Table(name = "tbl_user_payment_method")
public class PaymentMethod extends BaseEntity{

    @Column(name = "provider")
    private String provider; // Ví dụ: Momo, ZaloPay, Visa, MasterCard, PayPal, ...

    @Column(name = "account_number")
    private String account_number; // Số tài khoản hoặc số thẻ

    @Column(name = "account_name")
    private String account_name; // Tên chủ tài khoản hoặc tên trên thẻ

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "payment_type_id")
    private PaymentType paymentType;
}
