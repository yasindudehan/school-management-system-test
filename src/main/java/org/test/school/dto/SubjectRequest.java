package org.test.school.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SubjectRequest {

    private String name;
    private String code;
    private Long lectureId;
    private Long courseId;
    private String credit;
}
