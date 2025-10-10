package org.test.school.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseRequest {
    @NotBlank(message = "Name is required")
    private String name;
    private String startDate;
    private String endDate;
    @NotBlank(message = "Code is required")
    private String code;
}
