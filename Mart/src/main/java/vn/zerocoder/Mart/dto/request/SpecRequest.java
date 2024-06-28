package vn.zerocoder.Mart.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecRequest {

    private Long id;

    @NotBlank(message = "Tên thuộc tính không được để trống")
    private String name;

    private String description;
}
