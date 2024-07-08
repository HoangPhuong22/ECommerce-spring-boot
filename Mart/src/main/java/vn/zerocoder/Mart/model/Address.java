package vn.zerocoder.Mart.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "province")
    private String province; // Tỉnh/Thành phố

    @Column(name = "district")
    private String district; // Quận/Huyện

    @OneToOne(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE,
                CascadeType.DETACH,
                CascadeType.REFRESH
            })
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return province + ", " + district;
    }
}
