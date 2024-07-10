package vn.zerocoder.Mart.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_payment_type")
public class PaymentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name; // Ví dụ: Tiền mặt, Chuyển khoản, Thẻ tín dụng, Thẻ ghi nợ, ...

    @Column(name = "description")
    private String description;

    @Column(name = "requires_account")
    private Boolean requiresAccount;
}
