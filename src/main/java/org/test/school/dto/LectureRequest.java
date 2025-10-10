package org.test.school.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LectureRequest {

    @NotBlank(message = "Name is required")
     private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
     private String email;
    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 10, message = "Phone number must be  10  digits")
     private String phoneNumber;
     private String joinDate;

}
