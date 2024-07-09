package vn.zerocoder.Mart.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import vn.zerocoder.Mart.enums.Gender;
import vn.zerocoder.Mart.validator.FileNotEmpty;
import vn.zerocoder.Mart.validator.MinAge;
import vn.zerocoder.Mart.validator.PhoneValid;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {
    private Long id;

    @NotBlank(message = "Vui lòng nhập họ của bạn")
    private String firstName;

    @NotBlank(message = "Vui lòng nhập tên của bạn")
    private String lastName;

    @PhoneValid
    private String phoneNumber;

    private MultipartFile profile_image;

    @NotNull(message = "Vui lòng chọn ngày sinh của bạn")
    @MinAge(value = 18, message = "Tuổi phải lớn hơn 18")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

}
