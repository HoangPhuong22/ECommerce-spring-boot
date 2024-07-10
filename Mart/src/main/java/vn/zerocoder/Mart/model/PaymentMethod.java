package vn.zerocoder.Mart.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
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
    private String accountNumber; // Số tài khoản hoặc số thẻ

    @Column(name = "account_name")
    private String accountName; // Tên chủ tài khoản hoặc tên trên thẻ

    @Column(name = "expired")
    private LocalDate expired; // Ngày hết hạn thẻ

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
