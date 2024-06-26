package vn.zerocoder.Mart.dto.request;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VariationRequest {

    private Long id;

    @NotNull(message = "Tên biến thể không được để trống")
    @Size(min = 2, max = 255, message = "Tên biến thể phải từ 6 đến 255 ký tự")
    private String name;

    private List<Long> options_id;
}
