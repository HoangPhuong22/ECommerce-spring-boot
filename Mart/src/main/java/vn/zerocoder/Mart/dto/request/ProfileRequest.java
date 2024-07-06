package vn.zerocoder.Mart.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import vn.zerocoder.Mart.enums.Gender;

import java.time.LocalDate;

public class ProfileRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Gender gender;

}
